package com.project1.sms.requestDTO;

import com.project1.sms.enumeration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


import java.util.Set;

public record UserUpdateRequest(
       @NotBlank String firstName,
       @NotBlank String fatherName,
        @NotBlank String grandFName,
        @NotEmpty Set<Role> roles,
        @NotBlank String phoneNum,
       @Email String email
) {
}
