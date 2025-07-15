package com.example.smartshift.controller;

import com.example.smartshift.model.Member;
import com.example.smartshift.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller to list all red-flagged members.
 */
@RestController
@RequestMapping("/flags")
public class FlagController {

    private final MemberService memberService;

    public FlagController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * GET /flags - list members with status "Red Flag".
     * @return list of red flagged members
     */
    @GetMapping
    public List<Member> getRedFlaggedMembers() {
        return memberService.getMembersByStatus("Red Flag");
    }
}