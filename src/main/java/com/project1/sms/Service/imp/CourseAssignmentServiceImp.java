package com.project1.sms.Service.imp;

import com.project1.sms.Service.CourseAssignmentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.Assessment;
import com.project1.sms.model.CourseAssignment;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Teacher;
import com.project1.sms.repository.AssessmentRepo;
import com.project1.sms.repository.CourseAssignmentRepo;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.TeacherRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Service
public class CourseAssignmentServiceImp implements CourseAssignmentService {

    private final CourseOfferingRepo offeringRepo;
    private final TeacherRepo  teacherRepo;
    private CourseAssignmentRepo assignmentRepo;
    private AssessmentRepo assessmentRepo;
    @Override
    public Map<String, Object> assignCourse(Long offeringId, String teacherId) {
        CourseOffering courseOffering = offeringRepo.findById(offeringId).orElseThrow(() -> new ApiException("course offering not found "));
        Teacher teacher = teacherRepo.findByUserUserId(teacherId).orElseThrow(() -> new ApiException("teacher is ot found"));

        CourseAssignment assignment = assignmentRepo.save( new CourseAssignment(courseOffering,teacher));


        return Map.of();
    }
}
