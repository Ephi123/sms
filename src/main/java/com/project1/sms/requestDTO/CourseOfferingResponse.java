package com.project1.sms.requestDTO;

import com.project1.sms.model.CourseOffering;

public record CourseOfferingResponse(String courseName,
                                     String courseCode,
                                     Integer section,
                                     String Department,
                                     String program) {
    public static CourseOfferingResponse from(CourseOffering offering){
         return new CourseOfferingResponse(
                 offering.getCourse().getCourseName(),
                 offering.getCourse().getCourseCode(),
                 offering.getSection().getSection(),
                 offering.getCourse().getDepartment().getDepName(),
                 offering.getProgram().getName().getLabel()
         );
    }
}
