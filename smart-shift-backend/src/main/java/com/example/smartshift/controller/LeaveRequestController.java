package com.example.smartshift.controller;

import com.example.smartshift.model.LeaveRequest;
import com.example.smartshift.service.LeaveRequestService;
import com.example.smartshift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller to manage leave requests.
 */
@RestController
@RequestMapping("/leave-request")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final MemberService memberService;

    public LeaveRequestController(LeaveRequestService leaveRequestService, MemberService memberService) {
        this.leaveRequestService = leaveRequestService;
        this.memberService = memberService;
    }

    /**
     * POST /leave-request - submit leave request.
     * @param leaveRequest leave request JSON with member id, date, hours
     * @return saved leave request
     */
    @PostMapping
    public ResponseEntity<LeaveRequest> submitLeaveRequest(@Valid @RequestBody LeaveRequest leaveRequest) {
        // Verify if member exists
        var memberOpt = memberService.findById(leaveRequest.getMember().getId());
        if (memberOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        leaveRequest.setStatus("Pending");
        leaveRequest.setMember(memberOpt.get());

        // Leave hours added to member's leaveHours for tracking
        var member = memberOpt.get();
        member.setLeaveHours(member.getLeaveHours() + leaveRequest.getHours());
        memberService.save(member);

        var savedLeave = leaveRequestService.submitLeaveRequest(leaveRequest);
        return ResponseEntity.ok(savedLeave);
    }

    /**
     * GET /leave-request - get all leave requests.
     * @return list of leave requests
     */
    @GetMapping
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestService.getAllLeaveRequests();
    }
}