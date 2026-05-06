package com.project1.sms.repository;

import com.project1.sms.dto.GradeDetailDTO;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Grade;
import com.project1.sms.model.Result;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grade,Long> {
    List<Grade> findByStudentAndAssessmentCourseOffering(Student student, CourseOffering courseOffering);
    List<Grade> findByStudentAndAssessmentCourseOfferingStudyYear(Student student,int year);
   List<Grade> findByAssessmentCourseOffering(CourseOffering offering);


    @Query("SELECT new com.project1.sms.dto.GradeDetailDTO(" +
            "g.student.userId,CONCAT (g.student.firstName,' ',g.student.midlName), " +
            "CONCAT(g.assessment.title,'(', g.assessment.WeightPercent, ')'), g.marksObtained) " +
            "FROM Grade g " +
            "WHERE g.assessment.courseOffering.id = :id " +
            "ORDER BY g.student.studentId ASC")
    List<GradeDetailDTO> findGradeDetails(@Param("id") Long id);

}
