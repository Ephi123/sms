package com.project1.sms.Service;

import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.requestDTO.RoleRequest;
import com.project1.sms.requestDTO.UserUpdateRequest;
import com.project1.sms.responseDto.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserResponse getCurrentUser(Authentication authentication);

     List<UserResponse> getAllUsers(int page,int size);
     UserResponse createUser(RegisterRequest request);
     UserResponse getUser(String userId);
     void updateUser(String userId,UserUpdateRequest request);
     void updateUserActiveStatus(String userId, Active status);
     void addRoles(String userId, RoleRequest request);

     List<UserResponse> getUserByRole(Role role);
}
