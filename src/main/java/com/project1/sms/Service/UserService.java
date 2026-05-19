package com.project1.sms.Service;

import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.responseDto.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    public UserResponse getCurrentUser(Authentication authentication);

    public List<UserResponse> getAllUsers();
    public UserResponse createUser(RegisterRequest request);
}
