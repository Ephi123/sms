package com.project1.sms.repository;

import com.project1.sms.enumeration.ProgramEnum;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollRepo extends JpaRepository<Enrollment,Long> {
    //Total student currently enrolled
    Integer  countByCourseOfferingAcademicYearAndCourseOfferingSem(Integer year,  Integer sem);

    //total student currently enrolled in there department
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.courseOffering.academicYear = :year " +
            "AND e.courseOffering.sem = :sem AND e.courseOffering.department.depName = :dept")
    Integer countEnrolledByDepartment(@Param("year") Integer year, @Param("sem") Integer sem, @Param("dept") String department);

    //total number of student of for each department and year
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.courseOffering.academicYear = :year " +
            "AND e.courseOffering.sem = :sem AND e.courseOffering.department.depName = :dept "+
            "AND e.courseOffering.studyYear = :year ")
    Integer countEnrolledByDepartmentAndStudyYear(@Param("year") Integer year, @Param("sem") Integer sem, @Param("dept") String department, @Param("year") Integer studyYear);


    // list of Enrolled student for each department ,year and section
    @Query("SELECT e FROM Enrollment e WHERE e.courseOffering.academicYear = :acaYear AND "+
            "e.courseOffering.sem = :sem AND e.courseOffering.department.depName = :depName AND "+
            "e.courseOffering.section.section =:sec AND e.courseOffering.studyYear = :stdYear")
    List<Enrollment> getEnrolledBySection(@Param("acaYear") Integer year, @Param("sem") Integer semester, @Param("depName") String department, @Param("sec") Integer section,@Param("stdYear") Integer studyYear);

    //total number of student of for each department, year, program
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.courseOffering.academicYear = :year " +
            "AND e.courseOffering.sem = :sem AND e.courseOffering.department.depName = :dept "+
            "AND e.courseOffering.studyYear = :year AND e.courseOffering.program.name = :pName ")
    Integer countEnrolledByDepartmentAndStudyYearAndProgram(@Param("year") Integer year, @Param("sem") Integer sem, @Param("dept") String department, @Param("year") Integer studyYear, @Param("pName") String program);

    @Query("SELECT e FROM Enrollment e WHERE e.courseOffering.academicYear = :year " +
            "AND e.courseOffering.sem = :sem AND e.courseOffering.department.depName = :dept "+
            "AND e.courseOffering.studyYear = :year AND e.courseOffering.program.name = :pName AND "+
            "e.courseOffering.section = :sec AND e.student.user.userId = :stdId")
    Optional<Enrollment> getEnrolledStudent(@Param("year") Integer year, @Param("sem") Integer sem, @Param("dept") String department, @Param("year") Integer studyYear, @Param("pName") ProgramEnum program, @Param("sec") Integer section, @Param("stdId") String studentId);

    List<Enrollment> findByCourseOffering(CourseOffering offering);
     boolean existByStudentUserUserId(String userId);


}
