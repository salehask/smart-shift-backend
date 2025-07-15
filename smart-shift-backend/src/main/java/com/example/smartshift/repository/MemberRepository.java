package com.example.smartshift.repository;

import com.example.smartshift.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Member entity CRUD operations.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Find all members who have status "Active".
     * @return list of active members
     */
    List<Member> findByStatus(String status);

    /**
     * Find all members who have status "Red Flag"
     * @return list of flagged members
     */
    List<Member> findByStatusEquals(String status);
}