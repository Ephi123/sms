package com.project1.sms.responseDto;

import com.project1.sms.model.Course;

public record CourseResponse(
        String courseName,
        String courseCode,
        Long id,
        String depName
) {
    public static CourseResponse from(Course course){
        return new CourseResponse(
          course.getCourseName(),
          course.getCourseCode(),
                course.getId(),
          course.getDepartment().getDepName()
        );
    }
}
