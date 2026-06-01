package com.project1.sms.seeder;

import com.project1.sms.apiException.ApiException;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SystemSeeder implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        UserEntity user = userRepo.findById(32L).orElseThrow(() -> new ApiException("user not found"));
                   user.setFirstLogin(false);
                 UserEntity saved =userRepo.save(user);
                   System.out.println(saved.getFirstLogin());



    }
}
