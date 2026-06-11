package com.project1.sms.responseDto;

import com.project1.sms.model.Student;

public record NewStudent(
                         String studentId,
                         Long id,
                         String fullName,
                         String department,
                         String program,
                         int year,
                         int sem
                        ) {

    public static NewStudent from(Student student){

        return new NewStudent(
                student.getUser().getUserId(),
                student.getId(),
                student.getUser().getFirstName() +" "+student.getUser().getMidlName() +" "+student.getUser().getLastName(),
                student.getDepartment().getDepName(),
                student.getProgram().getName().getLabel(),
                student.getCurrentYear(),
                student.getCurrentSem()
        );
    }
 }
