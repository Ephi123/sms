package com.project1.sms.seeder;

import com.project1.sms.apiException.ResourceNotFoundException;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.ProgramEnum;
import com.project1.sms.enumeration.Role;
import com.project1.sms.model.Department;
import com.project1.sms.model.Program;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.DepartmentRepo;
import com.project1.sms.repository.ProgramRepo;
import com.project1.sms.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SystemSeeder implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepo departmentRepo;
    private final ProgramRepo programRepo;
    @Override
    public void run(String... args) throws Exception {
//           UserEntity user = UserEntity.builder().
//                   userId("system").
//                   firstName("system").
//                   midlName("system").
//                   lastName("system").
//                   phone(null).
//                   email("system@zion.com").
//                   imageUrl(null).
//                   userName("system@zion.com").
//                   password(passwordEncoder.encode("system@123")).
//                   firstLogin(false).
//                   roles(EnumSet.of(Role.ADMIN)).
//                   isActive(Active.ACTIVE).
//                   build();
//           UserEntity saved=userRepo.save(user);
//           System.out.println("system successfully created :"+saved);

        //UserEntity user = userRepo.findById(32L).orElseThrow(() -> new ResourceNotFoundException("userNot found"));
//                   user.setPassword(passwordEncoder.encode("system@123"));
//                   user.setUserName("system@zion.com");
//                   UserEntity saved =userRepo.save(user);
//        System.out.println(saved);
//        Department department = new Department();
//        department.setDepName("computer Science");
//        Department saved=departmentRepo.save(department);
//        System.out.println(saved);
//        Program reg=Program.builder().name(ProgramEnum.REGULAR).semesterPerYear(2).build();
//        Program weekend=Program.builder().name(ProgramEnum.WEEKEND).semesterPerYear(3).build();
//        Program night = Program.builder().name(ProgramEnum.NIGHT).semesterPerYear(3).build();
//        programRepo.saveAll(List.of(reg,weekend,night));
//        programRepo.deleteById(152L);
//        programRepo.deleteById(153L);
//        programRepo.deleteById(154L);
//        programRepo.deleteById(162L);
//        programRepo.deleteById(163L);
//        programRepo.deleteById(164L);


    }
}
