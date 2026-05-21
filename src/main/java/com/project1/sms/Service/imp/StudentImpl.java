package com.project1.sms.Service.imp;

import com.project1.sms.Service.StudentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.domain.EthiopianCalendar;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.enumeration.StudentStatus;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
import com.project1.sms.requestDTO.StudentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class StudentImpl implements StudentService {
    private final ProgramRepo programRepo;
    private final DepartmentRepo departmentRepo;
    private final SectionRepo sectionRepo;
    private final StudentRepo studentRepo;
    private final UserRepo userRepo;
    @Override
    public void studentRegister(StudentRequest request) {
        Program program = programRepo.findByName(request.Program()).orElseThrow(() -> new ApiException("program is not found"));
        Department department = departmentRepo.findByDepName(request.department()).orElseThrow(() -> new ApiException("department is not found"));
        Section section = sectionRepo.findBySection(request.section()).orElseThrow(() -> new ApiException("section is not found"));
        int studentNum = studentRepo.countByDepartmentAndProgramAndSectionAndCurrentYear(department,program,section,request.year())+1;

        String pro = program.getName().getLabel().toUpperCase().substring(0,2)+"-";
        String dep = department.getDepName().toUpperCase().substring(0,2)+"-";
        String year = EthiopianCalendar.ethiopianYear()+"".substring(2)+"-";
        String sec = section.getSection()+"-";
        String studentId = "ZC-"+dep+pro+year+sec+studentNum;

        String userName = userNameGenerator(userRepo,request);
        UserEntity user = UserEntity.
                builder().
                userId(studentId).
                firstName(request.firstName())
                .midlName(request.fatherName()).
                lastName(request.lastName()).phone(request.PhoneNum())
                .email(null)
                .imageUrl(null)
                .userName(userName).
                password("default_"+request.firstName()).roles(Set.of(Role.STUDENT)).isActive(Active.ACTIVE).build();

        UserEntity userEntity = userRepo.save(user);

         Student std = Student.builder().department(department).user(userEntity).
                  program(program).section(section).
                  currentYear(request.year()).
                  currentSem(request.sem()).
                  studentStatus(StudentStatus.UNENROLL).build();

         studentRepo.save(std);



    }





    private  String userNameGenerator(UserRepo userRepo,StudentRequest request){
        StringBuilder specialName = new StringBuilder(request.firstName() + request.fatherName());
        String userName = specialName+"@zion.edu";
        boolean isUsernameExist = userRepo.existsByUserName(userName);
        int n = 1;
        while (isUsernameExist){
            specialName.append(n);
            userName =  specialName +"@zion.edu";
            isUsernameExist = userRepo.existsByUserName(userName);
            n++;
        }

        return userName;
    }



}
