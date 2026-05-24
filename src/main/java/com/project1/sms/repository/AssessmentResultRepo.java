package com.project1.sms.repository;

import com.project1.sms.dto.AssessmentResultDetailDTO;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.AssessmentResult;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentResultRepo extends JpaRepository<AssessmentResult,Long> {
    List<AssessmentResult> findByStudentAndAssessmentCourseOffering(Student student, CourseOffering courseOffering);
    List<AssessmentResult> findByStudentAndAssessmentCourseOfferingStudyYear(Student student, int year);
    List<AssessmentResult> findByAssessmentCourseOffering(CourseOffering offering);


    @Query("""
    SELECT new com.project1.sms.dto.AssessmentResultDetailDTO(
        ar.student.user.userId,
        CONCAT(ar.student.user.firstName, ' ', ar.student.user.midlName),
        ar.marksObtained,
        ar.id,
        CONCAT(ar.assessment.title, '(', ar.assessment.weightPercent, '%)')
    )
    FROM AssessmentResult ar
    WHERE ar.assessment.courseOffering.id = :id
    ORDER BY ar.student.user.firstName ASC
""")
    List<AssessmentResultDetailDTO> findGradeDetails(@Param("id") Long offeringId);

    @Query("""
    SELECT new com.project1.sms.dto.AssessmentResultDetailDTO(
        ar.student.user.userId,
        CONCAT(ar.student.user.firstName, ' ', ar.student.user.midlName),
        ar.marksObtained,
        ar.id,
        CONCAT(ar.assessment.title, '(', ar.assessment.weightPercent, '%)')
    )
    FROM AssessmentResult ar
    WHERE ar.assessment.courseOffering.id = :id
      AND ar.student.user.userId = :stdId
""")
    List<AssessmentResultDetailDTO> findStudentGradeDetails(
            @Param("id") Long offeringId,
            @Param("stdId") String stdId
    );
}