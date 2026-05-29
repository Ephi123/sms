package com.project1.sms.Service;

import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.UserEntity;
import com.project1.sms.requestDTO.ChangePasswordRequest;
import com.project1.sms.requestDTO.LoginRequest;
import com.project1.sms.repository.UserRepo;
import com.project1.sms.responseDto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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

    public void restPassword(String userName){
           UserEntity user = userRepo.findByUserName(userName).orElseThrow(() -> new ApiException("user not found"));
                     user.setPassword(passwordEncoder.encode("default_"+user.getFirstName()));
                     userRepo.save(user);
    }

   public AuthResponse  changePassword(ChangePasswordRequest request){
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.userName(),
                       request.oldPassword()
               )
       );

       UserEntity user = userRepo.findByUserName(request.userName())
               .orElseThrow(() ->
                       new UsernameNotFoundException("user not found"));

       user.setPassword(
               passwordEncoder.encode(request.newPassword())
       );

       user.setFirstLogin(false);

       UserEntity updatedUser = userRepo.save(user);

       return toAuthResponse(updatedUser);



   }
    public AuthResponse login(LoginRequest request) {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           request.userName(),
                           request.password()
                   )
           );

           UserEntity user = userRepo.findByUserName(request.userName()).orElseThrow(() -> new UsernameNotFoundException("Credential Error"));
           if (Boolean.TRUE.equals(user.getFirstLogin())) {
               return toAuthResponseForFirstLogin(user);
           }


           return toAuthResponse(user);
       }
        catch (DisabledException e) {

            throw new RuntimeException(
                    "Your account is inactive. Contact admin."
            );
       }


    }

    private AuthResponse toAuthResponse(UserEntity user) {
        JwtService.TokenResult tokenResult = jwtService.generateToken(user);
        return new AuthResponse("Bearer", tokenResult.token(), tokenResult.expiresAt(), user.getUserName(), user.getRoles());
    }

    private AuthResponse toAuthResponseForFirstLogin(UserEntity userEntity) {

        return new AuthResponse(null, null, null, userEntity.getUserName(), null);
    }
}
