package com.project1.sms.requestDTO;

import com.project1.sms.dto.UserDto;
import com.project1.sms.enumeration.ProgramEnum;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record StudentsRequest(
        List<UserDto> users,
        @NotBlank String department,
        ProgramEnum Program,
        int section,
        int year,
        int sem) {
}
