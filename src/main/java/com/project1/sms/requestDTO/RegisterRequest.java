package com.project1.sms.requestDTO;

import com.project1.sms.enumeration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String userName,
        @NotBlank @Size(min = 8, message = "Password must contain at least 8 characters") String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email String email,
        @NotNull Role role
) {
}

