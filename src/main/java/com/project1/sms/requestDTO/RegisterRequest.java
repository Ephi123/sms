package com.project1.sms.requestDTO;

import com.project1.sms.enumeration.Role;
import jakarta.validation.constraints.*;

import java.util.Set;

public record RegisterRequest(
        @NotBlank String firstName,
        @NotBlank String fatherName,
        @NotBlank String lastName,
        @NotEmpty(message = "At least one role is required")
        Set<Role> roles
) {

}

