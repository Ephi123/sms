package com.project1.sms.Service;

import com.project1.sms.requestDTO.StudentRequest;
import com.project1.sms.requestDTO.StudentsRequest;

public interface StudentService {

    void studentRegister(StudentRequest request);

    void studentsRegistered(StudentsRequest studentsRequest);
}
