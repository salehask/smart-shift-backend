package com.example.smartshift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a Member participating in shifts.
 */
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public void setId(Long id) {
        this.id = id;
    }
    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false, unique = true)
    private String phone;

    /**
     * Assigned working hours in the current tracked period.
     */
    @Column(nullable = false)
    private int assignedHours = 0;

    /**
     * Hours the member took leave for.
     */
    @Column(nullable = false)
    private int leaveHours = 0;

    /**
     * Status of the member: Active or Red Flag.
     */
    @Column(nullable = false)
    private String status = "Active";

    // Constructors, getters & setters

    public Member() {
    }

    public Member(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.assignedHours = 0;
        this.leaveHours = 0;
        this.status = "Active";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAssignedHours() {
        return assignedHours;
    }

    public void setAssignedHours(int assignedHours) {
        this.assignedHours = assignedHours;
    }

    public int getLeaveHours() {
        return leaveHours;
    }

    public void setLeaveHours(int leaveHours) {
        this.leaveHours = leaveHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}