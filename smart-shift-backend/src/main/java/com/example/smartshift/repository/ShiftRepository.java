package com.example.smartshift.repository;

import com.example.smartshift.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Shift entity CRUD operations.
 */
@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    /**
     * Finds all shifts for dates between start and end inclusive.
     * @param start start date inclusive
     * @param end end date inclusive
     * @return list of shifts within date range
     */
    List<Shift> findByDateBetween(LocalDate start, LocalDate end);

    /**
     * Deletes all shifts before the given cutoff date.
     * @param cutoff the date before which shifts should be deleted
     */
    @Modifying
    @Query("DELETE FROM Shift s WHERE s.date < :cutoff")
    void deleteByDateBefore(@Param("cutoff") LocalDate cutoff);
}
