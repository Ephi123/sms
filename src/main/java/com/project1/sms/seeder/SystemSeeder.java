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
//           UserEntity user = UserEntity.builder().
//                   userId("system").
//                   firstName("system").
//                   midlName("admin").
//                   lastName("user").
//                   phone(null).
//                   email("systen@zion.com").
//                   imageUrl(null).
//                   userName("system@Zion.com").
//                   password(passwordEncoder.encode("system@123")).
//                   firstLogin(false).
//                   roles(Set.of(Role.ADMIN)).
//                   isActive(Active.ACTIVE).
//                   build();
//           UserEntity saved=userRepo.save(user);
//           System.out.println("system successfully created :"+saved);


    }
}
