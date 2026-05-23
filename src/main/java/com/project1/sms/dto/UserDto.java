package com.project1.sms.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank String firstName;
    @NotBlank String fatherName;
    @NotBlank String lastName;

}
