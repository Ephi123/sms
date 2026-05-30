package com.project1.sms.Service;

import com.project1.sms.responseDto.TeacherWithDepartmentDTo;
import com.project1.sms.responseDto.UserResponse;

import java.util.List;

public interface TeacherService {
    List<UserResponse>  getUnregisteredTeachers();
    void registerTeacher(Long userId,Long departmentId);
    List<TeacherWithDepartmentDTo> getAllTeacherWithDepartment();
}
