package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;

import java.util.Set;

public record UserResponse(

        String userName,
        String firstName,
        String midlName,
        String lastName,
        String password,
        Set<Role> roles
) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(
                user.getUserName(),
                user.getFirstName(),
                user.getMidlName(),
                user.getLastName(),
                user.getPassword(),
                user.getRoles()
        );
    }
}
