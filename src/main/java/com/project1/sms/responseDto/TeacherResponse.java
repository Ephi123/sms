package com.project1.sms.responseDto;

import com.project1.sms.model.Teacher;

public record TeacherResponse(
        String userId,
        Long id,
        String name,
        String phoneNum

) {
    public static TeacherResponse from(Teacher teacher){
        return new TeacherResponse(
                teacher.getUser().getUserId(),
                teacher.getId(),
                teacher.getUser().getFirstName()+teacher.getUser().getMidlName(),
                teacher.getUser().getPhone()



        );
    }
}
