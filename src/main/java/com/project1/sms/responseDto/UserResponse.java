package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

public record UserResponse(
        String UserId,
        String userName,
        String firstName,
        String midlName,
        String lastName,
        String password,
        Set<Role> roles,
        Active active
) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(
                user.getUserId(),
                user.getUserName(),
                user.getFirstName(),
                user.getMidlName(),
                user.getLastName(),
                user.getPassword(),
                user.getRoles(),
                user.getIsActive()
        );
    }

}
