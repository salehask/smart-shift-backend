package com.example.smartshift.controller;

import com.example.smartshift.model.Member;
import com.example.smartshift.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Members.
 */

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * GET /members - fetch all members.
     * @return list of members
     */
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    /**
     * POST /members - add a new member.
     * @param member input member json with name and phone
     * @return created member
     */
    @PostMapping
    public ResponseEntity<Member> addMember(@Valid @RequestBody Member member) {
        Member savedMember = memberService.addMember(member);
        return ResponseEntity.ok(savedMember);
    }

    /**
     * DELETE /members/{id} - delete member by ID.
     * @param id member id
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable Long id) {
        return memberService.findById(id)
            .map(member -> {
                memberService.deleteMember(id);
                return ResponseEntity.noContent().build(); // returns ResponseEntity<Void>
            })
            .orElseGet(() -> ResponseEntity.notFound().build()); // now matches ResponseEntity<Void>
    }

}