package com.project1.sms.repository;

import com.project1.sms.model.CourseAssignment;
import com.project1.sms.model.Department;
import com.project1.sms.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseAssignmentRepo extends JpaRepository<CourseAssignment,Long> {

    List<CourseAssignment> findByTeacher(Teacher teacher);

    //currently assigned courseAssignments
    List<CourseAssignment> findByCourseOfferingDepartmentAndCourseOfferingSemAndCourseOfferingAcademicYear(Department department,Integer sem,Integer academicYear);

    //All Assigned courseAssignments
    List<CourseAssignment> findByCourseOfferingDepartment(Department department);
}
