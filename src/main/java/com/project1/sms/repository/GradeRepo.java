package com.project1.sms.repository;

import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Grade;
import com.project1.sms.model.Result;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grade,Long> {
    List<Result> findByStudentAndAssessmentCourseOffering(Student student, CourseOffering courseOffering);
    List<Result> findByStudentAndAssessmentCourseOfferingStudyYear(Student student,int year);


}
