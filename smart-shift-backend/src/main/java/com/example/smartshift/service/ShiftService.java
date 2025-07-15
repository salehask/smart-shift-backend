package com.example.smartshift.service;

import com.example.smartshift.model.Shift;
import com.example.smartshift.repository.ShiftRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing shift assignments.
 */
@Service
@Transactional
public class ShiftService {

    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    /**
     * Get all shifts from start date to end date inclusive.
     * @param start start date
     * @param end end date
     * @return list of shifts
     */
    public List<Shift> getShiftsBetween(LocalDate start, LocalDate end) {
        return shiftRepository.findByDateBetween(start, end);
    }

    /**
     * Save a shift.
     * @param shift shift to save
     * @return saved shift
     */
    public Shift saveShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    /**
     * Delete all shifts.
     */
    public void deleteAllShifts() {
        shiftRepository.deleteAll();
    }

    /**
     * Delete shifts older than the given date.
     * @param cutoffDate the date before which shifts will be deleted
     */
    public void deleteShiftsBefore(LocalDate cutoffDate) {
        shiftRepository.deleteByDateBefore(cutoffDate);
    }

    /**
     * Delete a specific shift.
     * @param shift the shift to delete
     */
    public void deleteShift(Shift shift) {
        shiftRepository.delete(shift);
    }
}
