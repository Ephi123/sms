package com.project1.sms.Service.imp;


import com.project1.sms.Service.UserService;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;
import java.util.List;

import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.responseDto.UserResponse;
import com.project1.sms.utillity.UserUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
   @Override
    public UserResponse getCurrentUser(Authentication authentication) {
        UserEntity user = userRepo.findByUserName(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    /**
     * permanent employer first registered here
     * if they teach need to register and have role as teacher
     */
    @Override
    public UserResponse createUser(RegisterRequest request) {
       StringBuilder specialName = new StringBuilder(request.firstName() + request.fatherName());
        String userName = specialName+"@zion.edu";
        boolean isUsernameExist = userRepo.existsByUserName(userName);
        int n = 1;
        while (isUsernameExist){
            specialName.append(n);
            userName =  specialName +"@zion.edu";
            isUsernameExist = userRepo.existsByUserName(userName);
            n++;
           }
        UserEntity userEntity =UserUtility.createUser(request,userRepo,userName,"Emp_"+specialName);

        UserEntity savedEntity = userRepo.save(userEntity);

        return UserResponse.from(savedEntity);



    }
}
