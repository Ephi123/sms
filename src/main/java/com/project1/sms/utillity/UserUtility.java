package com.project1.sms.utillity;

import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;
import com.project1.sms.requestDTO.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUtility {

    public static UserEntity createUser(RegisterRequest request , UserRepo userRepo,String userName, String userId){



        return UserEntity.builder().
                userId(userId)
                .userName(userName)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .midlName(request.fatherName())
                .password("default_"+request.firstName())
                .roles(request.roles())
                .build();
    }


}
