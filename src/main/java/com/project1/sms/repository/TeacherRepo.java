package com.project1.sms.repository;

import com.project1.sms.model.Department;
import com.project1.sms.model.Student;
import com.project1.sms.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByUserUserId(String teacherId);
    List<Teacher> findByDepartment(Department department);
    Optional<Teacher> findByUserId(Long userId);
}
