package com.example.smartshift.controller;

import com.example.smartshift.model.Shift;
import com.example.smartshift.service.ShiftService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller to expose shift assignments.
 */
@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    /**
     * GET /shifts - fetch shift assignments for last 2 weeks (past week + current week).
     * @return list of shifts
     */
    @GetMapping
    public List<Shift> getShiftsLastTwoWeeks() {
        LocalDate today = LocalDate.now();
        LocalDate twoWeeksAgo = today.minusWeeks(2);
        return shiftService.getShiftsBetween(twoWeeksAgo, today);
    }
}