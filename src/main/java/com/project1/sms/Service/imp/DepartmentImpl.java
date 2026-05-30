package com.project1.sms.Service.imp;

import com.project1.sms.Service.DepartmentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Department;
import com.project1.sms.model.Teacher;
import com.project1.sms.repository.DepartmentRepo;
import com.project1.sms.repository.TeacherRepo;
import com.project1.sms.responseDto.DepartmentResponse;
import com.project1.sms.responseDto.DepartmentWithHeadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DepartmentImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;
    private final TeacherRepo teacherRepo;

    @Override
    public List<DepartmentResponse> getDepartments() {
      List<Department> departments = departmentRepo.findAll();
      return departments.stream().
              map(DepartmentResponse::from).toList();

    }

    @Override
    public DepartmentResponse getDepartment(Long departmentId) {
       Department department = departmentRepo.findById(departmentId).orElseThrow(() -> new ApiException("department is not found"));

        return DepartmentResponse.from(department);
    }

    @Override
    public void createDepartment(String depName) {
        Department department = new Department();
                  department.setDepName(depName);
                  departmentRepo.save(department);
    }

    @Override
    public void updateDepartment(Long depId,String depName) {
       Department department = departmentRepo.findById(depId).orElseThrow(() -> new ApiException("department is not found"));
       department.setDepName(depName);
       departmentRepo.save(department);
    }

    @Override
    public List<DepartmentResponse> getDepartmentsWithNullHead() {
        List<Department> departments = departmentRepo.findByHeadIsNullOrderByDepNameAsc();
         return departments.stream().
                 map(DepartmentResponse::from).toList();
    }

    @Override
    public void setDepartmentHead(Long departmentId, Long teacherId) {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new ApiException("Teacher is not found"));
        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> new ApiException("department id not found"));
        department.setHead(teacher);
        departmentRepo.save(department);
    }

    @Override
    public List<DepartmentWithHeadResponse> getAllDepartmentWithHead() {
        return departmentRepo.findAllDepartmentsWithHead();
    }


}
