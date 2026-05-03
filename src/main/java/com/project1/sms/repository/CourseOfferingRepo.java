package com.project1.sms.repository;

import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Department;
import com.project1.sms.model.Program;
import com.project1.sms.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfferingRepo extends JpaRepository<CourseOffering,Long> {

   List<CourseOffering> findByAcademicYearAndStudyYearAndSemAndSectionAndProgramAndDepartment(Integer academicYear, Integer studyYear, Integer sem, Section section, Program program, Department department);
}
