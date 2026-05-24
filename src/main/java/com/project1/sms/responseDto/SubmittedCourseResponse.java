package com.project1.sms.responseDto;

import com.project1.sms.model.CourseAssignment;

public record SubmittedCourseResponse(String teacherFullName,
                                      String CourseName,
                                      Integer year,
                                      Integer sem,
                                      String depName,
                                      String Program,
                                      Integer section,

                                      long CourseAssignmentId


                                      ) {

    public static SubmittedCourseResponse from(CourseAssignment  assignment){
        return new SubmittedCourseResponse(
               assignment.getTeacher().getUser().getFirstName()+" "+assignment.getTeacher().getUser().getMidlName(),
                assignment.getCourseOffering().getCourse().getCourseName(),
                assignment.getCourseOffering().getStudyYear(),
                assignment.getCourseOffering().getSem(),
                assignment.getCourseOffering().getDepartment().getDepName(),
                assignment.getCourseOffering().getProgram().getName().getLabel(),
                assignment.getCourseOffering().getSection().getSection(),
                assignment.getId()

        );
    }
}
