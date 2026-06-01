package com.project1.sms.controller;


import com.project1.sms.Service.AuthService;
import com.project1.sms.requestDTO.ChangePasswordRequest;
import com.project1.sms.requestDTO.LoginRequest;
import com.project1.sms.response.GlobalResponse;
import com.project1.sms.responseDto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("permitAll()")
//    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
//        return authService.register(request);
//    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GlobalResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(GlobalResponse.success("Authenticated successfully",authResponse));
    }
    @PostMapping("/change-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<GlobalResponse<AuthResponse>> change(@Valid @RequestBody ChangePasswordRequest request) {
       AuthResponse authResponse = authService.changePassword(request);
       return ResponseEntity.ok(GlobalResponse.success("Password successfully Changed",authResponse));

  }

    @PostMapping("/{userName}/rest-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalResponse<?>> restPassword(@PathVariable String userName) {
             authService.restPassword(userName);
        return ResponseEntity.ok(GlobalResponse.success("Password reset",null));

    }
}
