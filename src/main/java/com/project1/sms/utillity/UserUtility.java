package com.project1.sms.utillity;

import com.project1.sms.domain.EthiopianCalendar;
import com.project1.sms.model.Department;
import com.project1.sms.model.Program;
import com.project1.sms.model.Section;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.UserRepo;
import com.project1.sms.requestDTO.RegisterRequest;
import com.project1.sms.requestDTO.StudentRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUtility {

    public static UserEntity createUser(RegisterRequest request , UserRepo userRepo,String userName, String userId){



        return UserEntity.builder().
                userId(userId)
                .userName(userName)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .midlName(request.fatherName())
                .password("default_"+request.firstName())
                .roles(request.roles())
                .build();
    }

    public static String studentIdGenerator(Program program,
                                            Department department,
                                            Section section,
                                            int studentNum){

        String pro = program.getName().getLabel().toUpperCase().substring(0,2)+"-";
        String dep = department.getDepName().toUpperCase().substring(0,2)+"-";
        String year = EthiopianCalendar.ethiopianYear()+"".substring(2)+"-";
        String sec = section.getSection()+"-";

        return "ZC-"+dep+pro+year+sec+studentNum;

    }


    public static   String userNameGenerator(UserRepo userRepo, String firstName, String fatherName){
        StringBuilder specialName = new StringBuilder(firstName + fatherName);
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
