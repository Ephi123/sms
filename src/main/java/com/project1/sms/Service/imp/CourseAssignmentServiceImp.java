package com.project1.sms.Service.imp;

import com.project1.sms.Service.CourseAssignmentService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
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
    private final CourseAssignmentRepo assignmentRepo;
    private final CourseStatusRepo statusRepo;
    @Override
    public Map<String, Object> assignCourse(Long offeringId, String teacherId) {
        CourseOffering courseOffering = offeringRepo.findById(offeringId).orElseThrow(() -> new ApiException("course offering not found "));
        Teacher teacher = teacherRepo.findByUserUserId(teacherId).orElseThrow(() -> new ApiException("teacher is ot found"));

        CourseAssignment assignment = assignmentRepo.save( new CourseAssignment(courseOffering,teacher));
            statusRepo.save(new CourseStaus(courseOffering,0));

        return Map.of();
    }
}
