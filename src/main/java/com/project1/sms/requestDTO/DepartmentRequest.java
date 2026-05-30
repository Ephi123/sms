package com.project1.sms.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(
        @NotBlank String departmentName
) {
}
