package com.project1.sms.Service;

import com.project1.sms.requestDTO.StudentRequest;
import com.project1.sms.requestDTO.StudentsRequest;
import com.project1.sms.responseDto.NewStudent;

import java.util.List;

public interface StudentService {

    void studentRegister(StudentRequest request);

    void studentsRegistered(StudentsRequest studentsRequest);
    List<NewStudent> getNewStudent();
}
