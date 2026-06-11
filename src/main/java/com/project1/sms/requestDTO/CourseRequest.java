package com.project1.sms.requestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRequest(

         @NotBlank String courseName,
         @NotBlank String courseCode,
         @NotNull Integer creditHour

) {
}
