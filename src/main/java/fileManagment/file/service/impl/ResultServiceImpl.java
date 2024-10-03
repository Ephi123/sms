package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.repository.AssessmentRepo;
import fileManagment.file.repository.ResultRepo;
import fileManagment.file.repository.SemesterRepo;
import fileManagment.file.repository.SubjectRepo;
import fileManagment.file.responseDto.ResultResponse;
import fileManagment.file.service.ResultService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fileManagment.file.constant.Constant.*;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService {
    private final AssessmentRepo assessmentRepo;
    private final SubjectRepo subjectRepo;
    private final ResultRepo resultRepo;
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    private final SemesterRepo  semesterRepo;


    @Override
    @PreAuthorize("hasRole('USER')")
    public Map<String,?> getResultBySubject(String subName,String sec) {
        var assessments = assessmentRepo.findAssessmentsBySection(sec, ACADEMIC_YEAR,subName);

        int sem = semesterRepo.findByIsCurrent(true).getSemester();
        var assessmentResults = resultRepo.findByAssessmentSubject(subName,ACADEMIC_YEAR,"active").orElseThrow(() -> new ApiException("Assessment is not defined"));
        System.out.println("AssessmentResult "+assessmentResults);

        List<Map<String,?>> resultS = new ArrayList<>();
        List<Map<String,?>> marks = new ArrayList<>();
        int i=1;
        for(ResultResponse response1: assessmentResults){

                marks.add(Map.of("mark",response1.getMark(),"resultId",response1.getId(),"studentId",response1.getUserId(),"assessmentId",response1.getAssessmentId()));
            if(i == assessments.size()){

               resultS.add(
                       Map.of("mark",marks,"student", Map.of(
                                       "Id",response1.getUserId(),
                                       "firstName",response1.getFirstName(),
                                       "FatherName",response1.getLastName())
                       ));
                marks = new ArrayList<>();
            i=1;
            }

            i++;

        }

        return Map.of(
                "results",resultS,
                "assessment",assessments
        );
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void updateResult(Long resultId, double res) {
        var result = resultRepo.findById(resultId).orElseThrow(() -> new ApiException("result is not found"));
    if(res > result.getAssessment().getWight()){
        RequestUtil.handleErrorResponse(request,response,new ApiException(res+" is greater than assessment Weight"), HttpStatus.BAD_REQUEST);
         throw new ApiException("is greater than assessment Weight");
    }
     result.setMark(res);
     resultRepo.save(result);

    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<ResultResponse> getResultById(String subjectName,String userId) {
        var semesterEntity  = semesterRepo.findByIsCurrent(true);
        return resultRepo.findByAssessmentSubjectAndStudentId(subjectName, ACADEMIC_YEAR,"active",userId)
                .orElseThrow(() -> {
                    RequestUtil.handleErrorResponse(request,response,new ApiException("assessment is not created yet"), HttpStatus.BAD_REQUEST);
                    return  new ApiException("is greater than assessment Weight");
                }
                );

    }


}
