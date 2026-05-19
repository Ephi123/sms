package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;

public record UserResponse(
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email,
        Role role
) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
