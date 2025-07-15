package com.example.smartshift.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity representing a Shift assignment for two members in a time slot.
 */
@Entity
@Table(name = "shifts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "timeSlot"})
})
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date of the shift.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * Time slot for the shift (ex: "9-12").
     */
    @Column(nullable = false)
    private String timeSlot;

    /**
     * First member assigned to the shift.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member1_id", nullable = false)
    private Member member1;

    /**
     * Second member assigned to the shift.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member2_id", nullable = false)
    private Member member2;

    // Constructors, getters & setters

    public Shift() {
    }

    public Shift(LocalDate date, String timeSlot, Member member1, Member member2) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.member1 = member1;
        this.member2 = member2;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Member getMember1() {
        return member1;
    }

    public void setMember1(Member member1) {
        this.member1 = member1;
    }

    public Member getMember2() {
        return member2;
    }

    public void setMember2(Member member2) {
        this.member2 = member2;
    }
}