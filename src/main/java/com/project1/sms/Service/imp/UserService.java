package com.project1.sms.Service.imp;


import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;
import java.util.List;

import com.project1.sms.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

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
}
