package com.project1.sms.repository;

import com.project1.sms.model.Course;
import com.project1.sms.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {

    List<Course> findByDepartment(Department department);


    Optional<Course> findByCourseCode(String courseCode);
}
