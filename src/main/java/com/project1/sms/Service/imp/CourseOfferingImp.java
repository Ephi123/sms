package com.project1.sms.Service.imp;

import com.project1.sms.Service.CourseOfferingService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.domain.EthiopianCalendar;
import com.project1.sms.model.*;
import com.project1.sms.repository.*;
import com.project1.sms.requestDTO.CourseOfferingRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class CourseOfferingImp implements CourseOfferingService {
    private final CourseOfferingRepo offeringRepo;
    private final DepartmentRepo departmentRepo;
    private final SectionRepo sectionRepo;
    private final CourseRepo courseRepo;
    private final ProgramRepo programRepo;
    private final AssessmentRepo assessmentRepo;
    private final CurrentSemRepo semRepo;

    @Override
    public Map<String, Object> createCourseOffering(CourseOfferingRequest request) {
        Department department = departmentRepo.findByDepName(request.getDepartment()).orElseThrow(() -> new ApiException("Department is not found"));
        Section section = sectionRepo.findById(request.getSectionId()).orElseThrow(() -> new ApiException("section os not found"));
        Course course = courseRepo.findByCourseCode(request.getCourseCode()).orElseThrow(() -> new ApiException("Course not Found"));
        Program program = programRepo.findByName(request.getProgram()).orElseThrow(() -> new ApiException("program is not found"));
        int sem = semRepo.findAll().get(0).getCurrentSem();

        CourseOffering offering =offeringRepo.save(new CourseOffering(
                course,
                department,
                program,
                request.getYear(),
                sem,
                section,
                EthiopianCalendar.ethiopianYear()
                ));

        assessmentRepo.save(new Assessment(offering,"Mid",20));
        assessmentRepo.save(new Assessment(offering,"Assignment",20));
        assessmentRepo.save(new Assessment(offering,"Quiz",10));
        assessmentRepo.save(new Assessment(offering,"Attendance",5));
        assessmentRepo.save(new Assessment(offering,"final",45));

        return Map.of("data",offering);
    }
}
