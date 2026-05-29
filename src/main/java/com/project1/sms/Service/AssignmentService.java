package com.project1.sms.Service;

import com.project1.sms.requestDTO.AssignmentUpdateRequest;
import com.project1.sms.responseDto.AssignmentResponse;

import java.util.List;

public interface AssignmentService{
    List<AssignmentResponse> getAssignmentBeforeDueDate(Long offeringId);
    List<AssignmentResponse> getAssignmentAfterSubmission(Long offeringId);
    void updateAssignmentId(Long assignmentId,AssignmentUpdateRequest request);
}
