package com.project1.sms.Service.imp;

import com.project1.sms.Service.AssignmentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Assignment;
import com.project1.sms.repository.AssignmentRepo;
import com.project1.sms.requestDTO.AssignmentUpdateRequest;
import com.project1.sms.responseDto.AssignmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentImpl implements AssignmentService {
    private final AssignmentRepo assignmentRepo;
    @Override
    public List<AssignmentResponse> getAssignmentBeforeDueDate(Long offeringId) {
        List<Assignment> assignments = assignmentRepo.findActiveAssignments(offeringId);
        return assignments.stream().map(AssignmentResponse::from).toList();
    }
/**
 * teacher gets assigment after submission date and get assignment to download it
 *
 */
    @Override
    public List<AssignmentResponse> getAssignmentAfterSubmission(Long offeringId) {
        List<Assignment> assignments=assignmentRepo.findAssignmentAfterSubmission(offeringId);
        return assignments.stream().map(AssignmentResponse::from).toList();
    }

    @Override
    public void updateAssignmentId(Long assignmentId,AssignmentUpdateRequest request) {
        Assignment assignment =assignmentRepo.findById(assignmentId).orElseThrow(() -> new ApiException("assignment not found"));
        assignment.setTitle(request.title());
        assignment.setDescription(request.description());
        assignmentRepo.save(assignment);
    }


}
