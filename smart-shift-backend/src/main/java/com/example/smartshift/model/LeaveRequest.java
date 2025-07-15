package com.example.smartshift.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity representing a leave request by a member.
 */
@Entity
@Table(name = "leave_requests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Member who requested leave.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * Date of the leave.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * Number of hours requested for leave.
     */
    @Column(nullable = false)
    private int hours;

    /**
     * Status of the leave request (Pending, Compensated, etc.).
     */
    @Column(nullable = false)
    private String status = "Pending";

    // Constructors, getters & setters

    public LeaveRequest() {
    }

    public LeaveRequest(Member member, LocalDate date, int hours) {
        this.member = member;
        this.date = date;
        this.hours = hours;
        this.status = "Pending";
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}