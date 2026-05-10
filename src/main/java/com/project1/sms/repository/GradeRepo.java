package com.project1.sms.repository;

import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Grade;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<Grade,Long> {
    Grade findByStudentAndOffering(Student student, CourseOffering offering);
}
