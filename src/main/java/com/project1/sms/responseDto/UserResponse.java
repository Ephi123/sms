package com.project1.sms.responseDto;

import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

public record UserResponse(
        Long id,
        String UserId,
        String userName,
        String firstName,
        String midlName,
        String lastName,
        String phoneNum,
        String email,
        Set<Role> roles,
        Active active
) {

    public static UserResponse from(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserName(),
                user.getFirstName(),
                user.getMidlName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getRoles(),
                user.getIsActive()
        );
    }

}
