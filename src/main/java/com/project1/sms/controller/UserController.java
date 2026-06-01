package com.project1.sms.controller;

import java.util.List;
import java.util.Map;

import com.project1.sms.Service.imp.UserServiceImpl;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.requestDTO.RoleRequest;
import com.project1.sms.requestDTO.UserUpdateRequest;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GlobalResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        UserResponse user =userService.getCurrentUser(authentication);
        return ResponseEntity.ok(GlobalResponse.success("current user is sent", user));

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<List<UserResponse>>>  getAllUsers(  @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        List<UserResponse> users=userService.getAllUsers(page,size);
        return ResponseEntity.ok(GlobalResponse.success("users are sent", users));

    }
    @PostMapping
    public ResponseEntity<GlobalResponse<UserResponse>> createUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.success(HttpStatus.CREATED, "User created successfully", user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GlobalResponse<UserResponse>> getUser(@PathVariable String userId) {
        UserResponse user = userService.getUser(userId);
        return ResponseEntity.ok(GlobalResponse.success("User fetched successfully", user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<GlobalResponse<Void>> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request) {
        userService.updateUser(userId, request);
        return ResponseEntity.ok(GlobalResponse.<Void>success("User updated successfully", null));
    }

    @PatchMapping("/{userId}/active-status")
    public ResponseEntity<GlobalResponse<Void>> updateUserActiveStatus(
            @PathVariable String userId,
            @RequestParam Active status) {
        userService.updateUserActiveStatus(userId, status);
        return ResponseEntity.ok(GlobalResponse.<Void>success("User active status updated successfully", null));
    }

    @PatchMapping("/{userId}/roles")
    public ResponseEntity<GlobalResponse<Void>> addRoles(
            @PathVariable String userId,
            @Valid @RequestBody RoleRequest request) {
        userService.addRoles(userId, request);
        return ResponseEntity.ok(GlobalResponse.<Void>success("User roles updated successfully", null));
    }

    @GetMapping("/roles/{role}")
    public ResponseEntity<GlobalResponse< List<UserResponse>>> getUserByRole(@PathVariable Role role) {
        List<UserResponse> users = userService.getUserByRole(role);
        return ResponseEntity.ok(GlobalResponse.success("Users fetched by role successfully", users));
    }
}




