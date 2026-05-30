package com.project1.sms.Service.imp;

import com.project1.sms.Service.TeacherService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Department;
import com.project1.sms.model.Teacher;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.DepartmentRepo;
import com.project1.sms.repository.TeacherRepo;
import com.project1.sms.repository.UserRepo;
import com.project1.sms.responseDto.TeacherWithDepartmentDTo;
import com.project1.sms.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TeacherImpl implements TeacherService {
    private final TeacherRepo teacherRepo;
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;
    @Override
    public List<UserResponse> getUnregisteredTeachers() {
        List<UserEntity> users =userRepo.getUnregisteredTeachers();
        return users.stream().map(UserResponse::from).toList();
    }

    @Override
    public void registerTeacher(Long userId, Long departmentId) {
        Department department= departmentRepo.findById(departmentId).orElseThrow(() -> new ApiException("department is not found"));
       UserEntity user = userRepo.findById(userId).orElseThrow(() -> new ApiException("User not found"));
       Teacher teacher = Teacher.builder().department(department).user(user).build();
           teacherRepo.save(teacher);


    }

    @Override
    public List<TeacherWithDepartmentDTo> getAllTeacherWithDepartment() {
        return teacherRepo.getAllTeacherWithDepartment();
    }

}
