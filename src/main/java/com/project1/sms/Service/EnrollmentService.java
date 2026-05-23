package com.project1.sms.Service;
import com.project1.sms.model.Enrollment;

import java.util.List;
public interface EnrollmentService {

    Enrollment createEnrollment(Long studentId, Long  enrollmentId);

    Integer getNumberOfEnrolledStudentByDepartment(int sem, String department);

    Integer getTotalNumberOfEnrolledStudents(int sem);

    List<Enrollment> getEnrolledStudentBySection(int semester,  String department, int section, int studyYear );

    Integer getNumberOfEnrolledStudentByDepartmentAndYear(Integer sem, String department,  Integer studyYear);
    Integer getNumberOfEnrolledStudentByDepartmentAndYearAndProgram(Integer sem, String department,  Integer studyYear,String Program);



}
