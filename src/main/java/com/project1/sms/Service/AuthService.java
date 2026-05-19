package com.project1.sms.Service;

import com.project1.sms.model.UserEntity;
import com.project1.sms.requestDTO.LoginRequest;
import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.repository.UserRepo;
import com.project1.sms.responseDto.AuthResponse;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

//    @Transactional
//    public AuthResponse register(RegisterRequest request) {
//        if (userRepo.existsByUserName(request.userName())) {
//            throw new IllegalArgumentException("Username already exists");
//        }
//
//        UserEntity user = UserEntity.builder()
//                .userName(request.userName())
//                .password(passwordEncoder.encode(request.password()))
//                .firstName(request.firstName())
//                .lastName(request.lastName())
//                .email(request.email())
//                .roles(request.roles() == null ? Set.of() : request.roles())
//                .build();
//
//        UserEntity savedUser = userRepo.save(user);
//        return toAuthResponse(savedUser);
//    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.userName(), request.password())
        );

        UserEntity user = userRepo.findByUserName(request.userName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        return toAuthResponse(user);
    }

    private AuthResponse toAuthResponse(UserEntity user) {
        JwtService.TokenResult tokenResult = jwtService.generateToken(user);
        return new AuthResponse("Bearer", tokenResult.token(), tokenResult.expiresAt(), user.getUserName(), user.getRoles());
    }
}
