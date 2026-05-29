package com.project1.sms.responseDto;

import com.project1.sms.model.Assignment;

public record AssignmentResponse(

        String courseName,
        String title,
        String Description,
        Long assignmentId
) {

    public static AssignmentResponse from(Assignment assignment){
        return new AssignmentResponse(
                assignment.getOffering().getCourse().getCourseName(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getId()
        );
    }

}
