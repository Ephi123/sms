package com.project1.sms.repository;

import com.project1.sms.enumeration.CourseStatus;
import com.project1.sms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseAssignmentRepo extends JpaRepository<CourseAssignment,Long> {

    List<CourseAssignment> findByTeacher(Teacher teacher);

    //currently assigned courseAssignments
    List<CourseAssignment> findByCourseOfferingDepartmentAndCourseOfferingSemAndCourseOfferingAcademicYear(Department department,Integer sem,Integer academicYear);

    //All Assigned courseAssignments
    List<CourseAssignment> findByCourseOfferingDepartment(Department department);

    boolean existsByCourseOffering(CourseOffering offering);


    Optional<CourseAssignment> findByCourseOfferingId(Long courseOfferingId);
    List<CourseAssignment> findByCourseStatus(CourseStatus courseStatus);
    @Query("""
    SELECT COUN(c)
    FROM  CourseAssignment c
    Join c.courseOffering co
    WHERE co.academicYear = :academicYear
      AND co.studyYear = :studyYear
      AND co.sem = :sem
      AND co.section = :section
      AND co.program = :program
      AND co.department = :department
      AND c.courseStatus = :approvedStatus
""")
    int countApprovedCourses(
            @Param("academicYear") Integer academicYear,
            @Param("studyYear") Integer studyYear,
            @Param("sem") Integer sem,
            @Param("section") Section section,
            @Param("program") Program program,
            @Param("department") Department department,
            @Param("approvedStatus") CourseStatus status
    );


}
