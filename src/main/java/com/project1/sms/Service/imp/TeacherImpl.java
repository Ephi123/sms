package com.project1.sms.Service.imp;

import com.project1.sms.Service.TeacherService;
import com.project1.sms.apiException.ResourceNotFoundException;
import com.project1.sms.model.Department;
import com.project1.sms.model.Teacher;
import com.project1.sms.model.UserEntity;
import com.project1.sms.repository.DepartmentRepo;
import com.project1.sms.repository.TeacherRepo;
import com.project1.sms.repository.UserRepo;
import com.project1.sms.responseDto.TeacherWithDepartmentDTo;
import com.project1.sms.responseDto.UserResponse;
import com.project1.sms.security.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class TeacherImpl implements TeacherService {
    private final TeacherRepo teacherRepo;
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;
    private final CurrentUserService currentUserService;
    @Override
    public List<UserResponse> getUnregisteredTeachers() {
        List<UserEntity> users =userRepo.getUnregisteredTeachers();
        return users.stream().map(UserResponse::from).toList();
    }

    @Override
    public void registerTeacher(Long userId, Long departmentId) {
        Department department= departmentRepo.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException("department is not found"));
       UserEntity user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
       Teacher teacher = Teacher.builder().department(department).user(user).build();
           teacherRepo.save(teacher);


    }

    @Override
    public List<TeacherWithDepartmentDTo> getAllTeacherWithDepartment() {
        return teacherRepo.getAllTeacherWithDepartment();
    }

    @Override
    public List<TeacherWithDepartmentDTo> getTeacherWithTheirDepartment() {
        Department department = departmentRepo.findByHeadUserId(currentUserService.getUserId()).
                orElseThrow(() -> new ResourceNotFoundException("Department is not found"));
        List<Teacher> teachers =teacherRepo.findByDepartment(department);
        return teachers.stream().
                map(TeacherWithDepartmentDTo::)
    }

}
