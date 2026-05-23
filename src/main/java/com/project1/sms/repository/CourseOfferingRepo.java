package com.project1.sms.repository;

import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Department;
import com.project1.sms.model.Program;
import com.project1.sms.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfferingRepo extends JpaRepository<CourseOffering,Long> {

   List<CourseOffering> findByAcademicYearAndStudyYearAndSemAndSectionAndProgramAndDepartment(Integer academicYear, Integer studyYear, Integer sem, Section section, Program program, Department department);
   List<CourseOffering> findByAcademicYearAndSemAndDepartment(int academicYear, int sem, Department department);
   @Query("""
SELECT co FROM CourseOffering co
WHERE co.academicYear = :year
AND co.sem = :sem
AND co.department = :department
AND NOT EXISTS (
    SELECT a FROM CourseAssignment a
    WHERE a.courseOffering = co
)
""")
   List<CourseOffering> findUnassignedOfferings(
           int year,
           int sem,
           Department department
   );
}
