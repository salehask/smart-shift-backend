package com.example.smartshift.service;

import com.example.smartshift.model.LeaveRequest;
import com.example.smartshift.repository.LeaveRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing leave requests.
 */
@Service
@Transactional
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    /**
     * Submit a new leave request.
     * @param leaveRequest leave request to submit
     * @return saved leave request
     */
    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    /**
     * Get all leave requests.
     * @return list of leave requests
     */
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    /**
     * Find by id.
     * @param id id to find
     * @return Optional leave request
     */
    public Optional<LeaveRequest> findById(Long id) {
        return leaveRequestRepository.findById(id);
    }
}