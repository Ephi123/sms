package com.project1.sms.Service;

import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.responseDto.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserResponse getCurrentUser(Authentication authentication);

     List<UserResponse> getAllUsers();
     UserResponse createUser(RegisterRequest request);
}
