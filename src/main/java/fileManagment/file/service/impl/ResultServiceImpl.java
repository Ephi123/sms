package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.constant.Constant;
import fileManagment.file.entity.StudentPerformanceEntity;
import fileManagment.file.entity.SubjectStatusEntity;
import fileManagment.file.repository.*;
import fileManagment.file.responseDto.ResultResponse;
import fileManagment.file.responseDto.StudentResultDto;
import fileManagment.file.service.AssessmentService;
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
    private final Result result;
    private final SubjectStatusRepo subjectStatusRepo;
    private final AssessmentService assessmentService;
    private final UserRepo userRepo;
    private SectionRepo sectionRepo;
    private StudentPerformanceRepo performanceRepo;


    @Override
    @PreAuthorize("hasRole('USER')")//teacher//principal
    public Map<String,?> getResultBySubject(String subName,String sec) {
        var semesterEntity  = semesterRepo.findByIsCurrent(true);
        var assessments = assessmentRepo.findAssessmentsBySection(sec, ACADEMIC_YEAR,subName);

        int sem = semesterRepo.findByIsCurrent(true).getSemester();
        var assessmentResults = resultRepo.findByAssessmentSubject(subName,ACADEMIC_YEAR,semesterEntity.getSemester()).orElseThrow(() -> new ApiException("Assessment is not defined"));
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
    @PreAuthorize("hasRole('USER')")//teacher
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
    @PreAuthorize("hasRole('USER')")//student
    public List<ResultResponse> getResultById(String subjectName,String userId) {
        var semesterEntity  = semesterRepo.findByIsCurrent(true);
        return resultRepo.findByAssessmentSubjectAndStudentId(subjectName, ACADEMIC_YEAR,userId,semesterEntity.getSemester())
                .orElseThrow(() -> {
                    RequestUtil.handleErrorResponse(request,response,new ApiException("assessment is not created yet"), HttpStatus.BAD_REQUEST);
                    return  new ApiException("is greater than assessment Weight");
                }
                );

    }

    @Override
    @PreAuthorize("hasRole('USER')")//student
    public Map<String,?> getAvgAndRank(String userId) {

//        var res = result.studentAvgAndRank(userId, ACADEMIC_YEAR);
//        if(res.size() == 2){
//            var accumulativeResult = result.TwoSemCumulative(userId,ACADEMIC_YEAR);
//            return Map.of("result",res,"accumulative",accumulativeResult);
//        }
//      return Map.of("result",res);

    return null;
    }

    @Override
    @PreAuthorize("hasRole('USER')")//teacher
    public void teacherChangeStatus(String subName,String sec) {
          statusUpdate("waiting",sec,subName);

    }

    @Override
    @PreAuthorize("hasRole('USER')")//principal
    public void approvedStatus(String subName, String sec,Long teacherId) {
        var semesterEntity =semesterRepo.findByIsCurrent(true);
        int sem =semesterEntity.getSemester();
        statusUpdate("approved",sec,subName);
       var teacher = userRepo.findById(teacherId).orElseThrow(() -> new ApiException("teacher is not found"));
     var section = sectionRepo.findByRoom(sec).orElseThrow(() -> new ApiException("section is not found"));
     var subject = subjectRepo.findBySubjectName(subName).orElseThrow(() -> new ApiException("subject is not found"));
     var Grade = subject.getGrade();


       if(sem == 1){
         assessmentService.crateDefaultAssessment(subject,section,teacher,2);
           var status =SubjectStatusEntity.builder()
                   .sem(2)
                   .academicYear(ACADEMIC_YEAR)
                   .status("active")
                   .section(section)
                   .subject(subject)
                   .build();
           subjectStatusRepo.save(status);
     }
       var subjectNum = subjectStatusRepo.findApprovedSubject(ACADEMIC_YEAR,sec,"approved");
       if(subjectRepo.countByGrade(Grade) == subjectNum.size()){
           var results = result.studentAvgAndRankPerSem(sec,ACADEMIC_YEAR,1);
           List<StudentPerformanceEntity> studentPerformances = new ArrayList<>();
          for(StudentResultDto result : results){
              studentPerformances.add(studentPerformanceBuilder(result));
          }
          performanceRepo.saveAll(studentPerformances);
          semesterEntity.setSemester(2);
          semesterRepo.save(semesterEntity);

       }

        if(subjectRepo.countByGrade(Grade)*2 == subjectNum.size()){
            var results = result.studentAvgAndRankPerSem(sec,ACADEMIC_YEAR,2);
            List<StudentPerformanceEntity> studentPerformances = new ArrayList<>();
            for(StudentResultDto result : results){
                studentPerformances.add(studentPerformanceBuilder(result));
            }
            performanceRepo.saveAll(studentPerformances);
            semesterEntity.setSemester(1);
            semesterRepo.save(semesterEntity);
          //cumulative Average
            var cumulative = result.TwoSemCumulative(sec,ACADEMIC_YEAR);
            List<StudentPerformanceEntity> cumulativeStudentPerformances = new ArrayList<>();
            for(StudentResultDto result : cumulative){
                studentPerformances.add(studentPerformanceBuilder(result));
            }
            performanceRepo.saveAll(cumulativeStudentPerformances);

        }





    }

    @Override
    public List<SubjectStatusEntity> getSubjectWthWaitingStatus() {
        return subjectStatusRepo.findByStatus("waiting");


    }

    @Override
    @PreAuthorize("hasRole('USER')")//principal
    public void unapprovedStatus(String subName, String sec) {
            statusUpdate("active",sec,subName);

    }

  private void statusUpdate(String status,String subName, String sec){
      var semesterEntity =semesterRepo.findByIsCurrent(true);
      int sem =semesterEntity.getSemester();
      SubjectStatusEntity status1 = subjectStatusRepo.findSubjectEntityBySecSubSem(ACADEMIC_YEAR,sem,sec,subName);
      status1.setStatus(status);
      subjectStatusRepo.save(status1);
  }

  private StudentPerformanceEntity studentPerformanceBuilder(StudentResultDto resultDto){
     return StudentPerformanceEntity.builder()
              .year(ACADEMIC_YEAR)
              .sec(resultDto.getSec())
              .average(resultDto.getAverage())
              .fName(resultDto.getFName())
              .lName(resultDto.getLName())
              .sem(resultDto.getSem())
              .std_rank(resultDto.getStd_rank())
              .userId(resultDto.getUserId())
             .cumulativeAvg(resultDto.getCumulativeAvg())
             .cumulativeRank(resultDto.getCumulativeRank())
             .rankFromAll(resultDto.getRankFromAll())
              .build();
  }
}
