package com.example.smartshift.repository;

import com.example.smartshift.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for LeaveRequest entity.
 */
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    /**
     * Find all leave requests for a particular member.
     */
    List<LeaveRequest> findByMemberId(Long memberId);

    /**
     * Find all leave requests with given status.
     */
    List<LeaveRequest> findByStatus(String status);
}