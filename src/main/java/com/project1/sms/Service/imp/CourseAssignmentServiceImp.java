package com.project1.sms.Service.imp;

import com.project1.sms.Service.CourseAssignmentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.domain.EthiopianCalendar;
import com.project1.sms.enumeration.CourseStatus;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
import com.project1.sms.requestDTO.CourseOfferingResponse;
import com.project1.sms.responseDto.SubmittedCourseResponse;
import com.project1.sms.security.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Service
public class CourseAssignmentServiceImp implements CourseAssignmentService {
    private final  CurrentUserService currentUserService;
    private final CourseOfferingRepo offeringRepo;
    private final TeacherRepo  teacherRepo;
    private final CourseAssignmentRepo assignmentRepo;
    private final DepartmentRepo departmentRepo;
    private  final CurrentSemRepo semRepo;
    @Override
    public Map<String, Object> assignCourse(Long offeringId, String teacherId) {
        CourseOffering courseOffering = offeringRepo.findById(offeringId).orElseThrow(() -> new ApiException("course offering not found "));
        Teacher teacher = teacherRepo.findByUserUserId(teacherId).orElseThrow(() -> new ApiException("teacher is ot found"));

        CourseAssignment assignment = assignmentRepo.save( new CourseAssignment(courseOffering,teacher, CourseStatus.NOT_SUBMITTED));
        return Map.of();
    }

    @Override
    public List<CourseOfferingResponse> getUnassignedCourses() {
        Long userId = currentUserService.getUserId();
        Teacher teacher = teacherRepo.findByUserId(userId).orElseThrow(() -> new ApiException("teacher is not found"));
        Department department = departmentRepo.findByHead(teacher).orElseThrow(() -> new ApiException("Department is not found"));
        CurrentSem semester = semRepo.findTopByOrderByIdDesc()
                .orElseThrow(() -> new ApiException("Semester not found"));
        int sem = semester.getCurrentSem();

        List<CourseOffering> offerings =
                offeringRepo.findUnassignedOfferings(
                        EthiopianCalendar.ethiopianYear(),
                        sem,
                        department
                );

        return offerings.
                stream()
                .map(CourseOfferingResponse::from).
                toList();

    }

    @Override
    public List<SubmittedCourseResponse> getSubmittedCourse() {
       List<CourseAssignment> assignments = assignmentRepo.findByCourseStatus(CourseStatus.SUBMITTED);
       return  assignments.stream().map(SubmittedCourseResponse::from).toList();




}
}
