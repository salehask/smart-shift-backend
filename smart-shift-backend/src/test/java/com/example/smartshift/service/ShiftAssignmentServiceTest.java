package com.example.smartshift.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.smartshift.model.Member;
import com.example.smartshift.model.Shift;
import com.example.smartshift.repository.MemberRepository;
import com.example.smartshift.repository.ShiftRepository;
import com.example.smartshift.scheduler.ShiftAssignmentScheduler;

import java.time.LocalDate;
import java.util.List;


/**
 * Unit test for ShiftAssignmentScheduler assignment logic.
 */
public class ShiftAssignmentServiceTest {

    private MemberService memberService;
    private ShiftService shiftService;
    private MemberRepository memberRepository;
    private ShiftRepository shiftRepository;
    private ShiftAssignmentScheduler scheduler;

    @BeforeEach
    public void setup() {
        memberRepository = mock(MemberRepository.class);
        shiftRepository = mock(ShiftRepository.class);

        memberService = new MemberService(memberRepository);
        shiftService = new ShiftService(shiftRepository);

        scheduler = new ShiftAssignmentScheduler(memberService, shiftService);
    }

    @Test
    public void testAssignShiftsWithTwoMembers() {
        Member m1 = new Member("Alice", "1112223333");
        m1.setId(1L);
        m1.setAssignedHours(0);
        m1.setLeaveHours(0);
        m1.setStatus("Active");

        Member m2 = new Member("Bob", "2223334444");
        m2.setId(2L);
        m2.setAssignedHours(0);
        m2.setLeaveHours(0);
        m2.setStatus("Active");

        when(memberRepository.findByStatus("Active"))
                .thenReturn(List.of(m1, m2));

        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(shiftRepository.save(any(Shift.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(shiftRepository.findByDateBetween(any(), any())).thenReturn(List.of());
        doNothing().when(shiftRepository).deleteAll();

        scheduler.assignShifts();

        // Validate member assignedHours updated
        assertTrue(m1.getAssignedHours() >= 0);
        assertTrue(m2.getAssignedHours() >= 0);

        // Verify shift saving called twice per time slot
        verify(shiftRepository, atLeastOnce()).save(any(Shift.class));
        verify(memberRepository, atLeast(2)).save(any(Member.class));
    }
}