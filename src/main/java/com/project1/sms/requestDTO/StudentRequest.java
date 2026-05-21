package com.project1.sms.requestDTO;

import com.project1.sms.enumeration.ProgramEnum;
import com.project1.sms.model.Program;
import jakarta.validation.constraints.NotBlank;

public record StudentRequest (
        @NotBlank String firstName,
        @NotBlank String fatherName,
        @NotBlank String lastName,
        @NotBlank String PhoneNum,
        @NotBlank String department,
         ProgramEnum Program,
        int section,
         int year,
        int sem) {
}
