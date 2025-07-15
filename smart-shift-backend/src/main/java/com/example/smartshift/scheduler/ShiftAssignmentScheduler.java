package com.example.smartshift.scheduler;

import com.example.smartshift.model.Member;
import com.example.smartshift.model.Shift;
import com.example.smartshift.service.MemberService;
import com.example.smartshift.service.ShiftService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

/**
 * Scheduler that automatically assigns members to shift time slots every Monday to Saturday at 8:30 AM.
 */
@Component
public class ShiftAssignmentScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ShiftAssignmentScheduler.class);

    private static final int SHIFT_HOURS = 3;
    private static final List<String> DEFAULT_TIME_SLOTS = List.of("9-12", "12-15", "15-18", "18-21");

    private final MemberService memberService;
    private final ShiftService shiftService;
    private final List<String> timeSlots;

    public ShiftAssignmentScheduler(MemberService memberService, ShiftService shiftService) {
        this.memberService = memberService;
        this.shiftService = shiftService;
        // Use mutable copy to allow shuffling if needed
        this.timeSlots = new ArrayList<>(DEFAULT_TIME_SLOTS);
    }

    /**
     * Runs at 08:30 every Monday to Saturday.
     */
    @Scheduled(cron = "0 30 8 ? * MON-SAT")
    @Transactional
    public void assignShifts() {
        LocalDate today = LocalDate.now();
        logger.info("🚀 Starting shift assignment for {}", today);

        clearOldShifts(today);

        List<Member> members = new ArrayList<>(memberService.getMembersByStatus("Active"));

        if (members.isEmpty()) {
            logger.warn("⚠️ No active members found.");
            return;
        }

        Collections.shuffle(members);
        List<Shift> todayShifts = new ArrayList<>();

        int requiredMembers = timeSlots.size() * 2;
        if (members.size() < requiredMembers) {
            logger.warn("⚠️ Insufficient members. Required: {}, Found: {}", requiredMembers, members.size());
        }

        int memberIndex = 0;

        for (String slot : timeSlots) {
            Member member1 = getNextAvailableMember(members, memberIndex++);
            Member member2 = getNextAvailableMember(members, memberIndex++);

            if (member1 == null && member2 == null) {
                logger.warn("⚠️ No members for slot {} on {}", slot, today);
                continue;
            }

            if (member1 != null && member2 == null) {
                logger.warn("⚠️ Only one member ({}) assigned for slot {} on {}", member1.getName(), slot, today);
            } else if (member1 == null && member2 != null) {
                logger.warn("⚠️ Only one member ({}) assigned for slot {} on {}", member2.getName(), slot, today);
                member1 = member2;
                member2 = null;
            }

            Shift shift = new Shift(today, slot, member1, member2);
            todayShifts.add(shift);

            member1.setAssignedHours(member1.getAssignedHours() + SHIFT_HOURS);
            if (member2 != null) {
                member2.setAssignedHours(member2.getAssignedHours() + SHIFT_HOURS);
            }
        }

        shiftService.deleteShiftsBefore(today); // Custom method to delete shifts before today
        todayShifts.forEach(shiftService::saveShift);
        members.forEach(memberService::save);

        evaluateRedFlags(today);
        logger.info("✅ Completed shift assignment for {}", today);
    }

    /**
     * Deletes shifts older than 2 weeks from today.
     */
    private void clearOldShifts(LocalDate today) {
        LocalDate cutoff = today.minusWeeks(2);
        List<Shift> oldShifts = shiftService.getShiftsBetween(LocalDate.MIN, cutoff.minusDays(1));
        for (Shift s : oldShifts) {
            shiftService.deleteShift(s); // Make sure this method exists in ShiftService
        }
    }

    /**
     * Get member at index or null.
     */
    private Member getNextAvailableMember(List<Member> members, int idx) {
        return idx < members.size() ? members.get(idx) : null;
    }

    /**
     * Marks members as Red Flag if leave not compensated in 2 weeks.
     */
    private void evaluateRedFlags(LocalDate today) {
        List<Member> members = memberService.getMembersByStatus("Active");

        for (Member m : members) {
            int requiredHours = m.getLeaveHours(); // hours to be compensated
            if (requiredHours > 0 && m.getAssignedHours() < requiredHours) {
                m.setStatus("Red Flag");
                logger.info("⚠️ Member {} marked Red Flag. Leave: {}, Assigned: {}", m.getName(), m.getLeaveHours(), m.getAssignedHours());
            } else if (requiredHours > 0 && m.getAssignedHours() >= requiredHours) {
                m.setStatus("Active");
                m.setLeaveHours(0); // Reset leave after compensation
                logger.info("✅ Member {} compensated leave. Status reset.", m.getName());
            }
            memberService.save(m);
        }
    }
}
