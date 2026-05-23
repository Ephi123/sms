package com.project1.sms.controller;


import com.project1.sms.Service.AuthService;
import com.project1.sms.requestDTO.LoginRequest;
import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.responseDto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
