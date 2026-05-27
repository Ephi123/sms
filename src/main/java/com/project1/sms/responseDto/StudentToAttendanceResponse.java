package com.project1.sms.responseDto;

import com.project1.sms.model.Student;

public record StudentToAttendanceResponse(
        String studentId,
        String fullName
) {
    public static StudentToAttendanceResponse from(Student student){
        return new StudentToAttendanceResponse(
              student.getUser().getUserId(),
              student.getUser().getFirstName()+" "+student.getUser().getMidlName()+" "+student.getUser().getLastName()
        );
    }
}
