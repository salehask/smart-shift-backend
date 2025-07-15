package com.example.smartshift.service;

import com.example.smartshift.model.Member;
import com.example.smartshift.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing members.
 */
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Get all members.
     * @return list of members
     */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Add a new member.
     * @param member member to add
     * @return saved member
     */
    public Member addMember(Member member) {
        member.setStatus("Active");
        member.setAssignedHours(0);
        member.setLeaveHours(0);
        return memberRepository.save(member);
    }

    /**
     * Delete a member by ID.
     * @param id member id
     */
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    /**
     * Find member by id.
     * @param id member id
     * @return Optional member
     */
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    /**
     * Save or update a member.
     * @param member member to save
     * @return saved member
     */
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    /**
     * Get all members by status.
     * @param status string status
     * @return list of members
     */
    public List<Member> getMembersByStatus(String status) {
        return memberRepository.findByStatus(status);
    }
}