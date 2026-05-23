package com.project1.sms.Service.imp;

import com.project1.sms.Service.StudentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.dto.UserDto;
import com.project1.sms.enumeration.Active;
import com.project1.sms.enumeration.Role;
import com.project1.sms.enumeration.StudentStatus;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
import com.project1.sms.requestDTO.StudentRequest;
import com.project1.sms.requestDTO.StudentsRequest;
import com.project1.sms.utillity.UserUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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


        String studentId = UserUtility.studentIdGenerator(program,department,section,studentNum);

        String userName = UserUtility.userNameGenerator(userRepo,request.firstName(),request.fatherName());
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

    @Override
    public void studentsRegistered(StudentsRequest studentsRequest) {
        Program program = programRepo.findByName(studentsRequest.Program()).orElseThrow(() -> new ApiException("program is not found"));
        Department department = departmentRepo.findByDepName(studentsRequest.department()).orElseThrow(() -> new ApiException("department is not found"));
        Section section = sectionRepo.findBySection(studentsRequest.section()).orElseThrow(() -> new ApiException("section is not found"));
        int studentNum = studentRepo.countByDepartmentAndProgramAndSectionAndCurrentYear(department,program,section,studentsRequest.year())+1;
      List<UserEntity> users = new ArrayList<>();
        int temp = studentNum;
      for(UserDto userDto: studentsRequest.users()){
            users.add(UserEntity.builder().
                  userId(UserUtility.studentIdGenerator(program, department,section,temp)).
                  firstName(userDto.getFirstName()).
                  midlName(userDto.getFatherName()).
                  lastName(userDto.getLastName()).
                  phone(null).email(null).imageUrl(null).
                  userName(UserUtility.userNameGenerator(userRepo,userDto.getFirstName(),userDto.getFatherName())).password("default_"+userDto.getFirstName()).
                  roles(Set.of(Role.STUDENT)).isActive(Active.ACTIVE).
                  build());
            temp++;


      }

      List<UserEntity> savedUsers = userRepo.saveAll(users);

     List<Student> students = savedUsers.stream().map(
             userEntity ->Student.builder().department(department).user(userEntity)
             .program(program).section(section).
             currentYear(studentsRequest.year()).currentSem(studentsRequest.sem()).
             studentStatus(StudentStatus.UNENROLL).build()).toList();
     studentRepo.saveAll(students);


    }





}
