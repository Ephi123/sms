package com.project1.sms.requestDTO;

import jakarta.validation.constraints.NotBlank;

public record AssignmentUpdateRequest(
        @NotBlank String title,
        @NotBlank String description
) {
}
