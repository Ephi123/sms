package com.project1.sms.Service;

import com.project1.sms.requestDTO.DepartmentRequest;
import com.project1.sms.responseDto.DepartmentResponse;
import com.project1.sms.responseDto.DepartmentWithHeadResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getDepartments();
    DepartmentResponse getDepartment(Long departmentId);
    void createDepartment(String depName);
    void updateDepartment(Long depId,String depName);
    List<DepartmentResponse> getDepartmentsWithNullHead();
    void setDepartmentHead(Long departmentId,Long teacherId);
    List<DepartmentWithHeadResponse> getAllDepartmentWithHead();
}
