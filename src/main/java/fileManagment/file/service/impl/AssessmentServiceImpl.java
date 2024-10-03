package fileManagment.file.service.impl;
import fileManagment.file.apiException.ApiException;
import fileManagment.file.entity.*;
import fileManagment.file.repository.*;
import fileManagment.file.requestDto.AssessmentRequest;
import fileManagment.file.service.AssessmentService;
import fileManagment.file.util.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static fileManagment.file.constant.Constant.*;
import static fileManagment.file.util.AssessmentUtil.*;
import static fileManagment.file.util.RequestUtil.handleErrorResponse;
@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {
    private final AssessmentRepo assessmentRepo;
    private final EnrolRepository enrolRepository;
    private  final SemesterRepo semesterRepo;
    private final ResultRepo  resultRepo;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final UserRepo userRepo;
    @Override
    public void crateDefaultAssessment(SubjectEntity sub, SectionEntity sec, UserEntity teacher) {
        SemesterEntity semester = semesterRepo.findByIsCurrent(true);
                       int seme =semester.getSemester();
        List<AssessmentEntity> assessmentEntities = createDefaultAssessments(sub,sec,teacher,seme);
        generateDefaultMark(assessmentEntities, sec.getRoom());

    }

    @Override
    @PreAuthorize("hasRole('USER')")//Teacher role
    public void updateAssessment(AssessmentRequest assessmentRequest,String subject,String sec) {
         AssessmentEntity assessment  = assessmentRepo.findById(assessmentRequest.getId()).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("assessment is not found "), HttpStatus.BAD_REQUEST);
               return new ApiException("assessment is not found");}
        );
        List<UserEntity> students = enrolRepository.findStudentsBySection(assessmentRequest.getSection(), ACADEMIC_YEAR, PageRequest.of(0,45))
                .get()
                .toList();
        if(isAssessmentValid(subject,sec,assessmentRequest)){
              if(assessmentRequest.getAssessmentName() == null && assessmentRequest.getWight() == null){
                  handleErrorResponse(request, response, new ApiException("assessment is not update "), HttpStatus.BAD_REQUEST);
                  throw new ApiException("assessment is not update");
              }
            update(assessmentRequest,assessment);

        }else{
            handleErrorResponse(request, response, new ApiException("assessment  wight is greater than 100 "), HttpStatus.BAD_REQUEST);
            throw new ApiException("assessment  wight is greater than 100 ");
        }


    }

    @Override
    @PreAuthorize("hasRole('USER')")//Teacher role
    public void deleteAssessment(Long id) {
       assessmentRepo.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void addAssessment(AssessmentRequest assessmentRequest) {
        SemesterEntity semester = semesterRepo.findByIsCurrent(true);
        int seme =semester.getSemester();
        List<UserEntity> students = enrolRepository.findStudentsBySection(assessmentRequest.getSection(), ACADEMIC_YEAR, PageRequest.of(0,45))
                .get()
                .toList();
        if(students.isEmpty()){
            handleErrorResponse(request, response, new ApiException("student is not registered in this class"), HttpStatus.BAD_REQUEST);
            throw new ApiException("student is not registered in this class");
        }

        var subject1 = subjectRepo.findBySubjectName(assessmentRequest.getSubject()).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("Subject is not find"), HttpStatus.BAD_REQUEST);
          return   new ApiException("subject is not find");
        });


        var section = sectionRepo.findByRoom(assessmentRequest.getSection()).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("section is not find"), HttpStatus.BAD_REQUEST);
           return   new ApiException("section is not find");
        });

        var teacher = userRepo.findByUserId(assessmentRequest.getTeacher()).orElseThrow(() -> {
            handleErrorResponse(request, response, new ApiException("teacher is not find"), HttpStatus.BAD_REQUEST);
           return new ApiException("teacher is not find");
        });
        List<AssessmentEntity> assessments = assessmentRepo.findAssessmentsBySection(assessmentRequest.getSection(), ACADEMIC_YEAR, assessmentRequest.getSubject());
         int wightTotal = 0;
        for(AssessmentEntity ass: assessments)
                wightTotal += ass.getWight();

        List<ResultEntity> results = new ArrayList<>();
        if(wightTotal+assessmentRequest.getWight() <= 100){
            var assessment = assessmentRepo.save(assessment(subject1,section,teacher,seme,assessmentRequest.getAssessmentName(),assessmentRequest.getWight()));
            for(UserEntity student: students) {
                results.add(ResultUtil.result(assessment,student,0));
            }

            resultRepo.saveAll(results);
        }




        else {
            handleErrorResponse(request, response, new ApiException("you can't create assessment"), HttpStatus.BAD_REQUEST);
            throw  new ApiException("you can't create assessment");
        }




    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<AssessmentEntity> getAssessments(String sec,String sub) {
        return assessmentRepo.findAssessmentsBySection(sec, ACADEMIC_YEAR,sub);
    }

    private boolean isAssessmentValid(String subject,String sec,AssessmentRequest assessmentDto) {
                   if(subject == null || sec ==null)
                         return false;
        List<AssessmentEntity> assessments = assessmentRepo.findAssessmentsBySection(sec, ACADEMIC_YEAR,subject);
        int wightTotal = 0;
        for(AssessmentEntity assessment: assessments){

            if(Objects.equals(assessment.getId(), assessmentDto.getId()) && assessmentDto.getWight() != null){
                    wightTotal += assessmentDto.getWight();
                    continue;
            }
           wightTotal += assessment.getWight();
        }

        return wightTotal <= 100;
    }

    private void generateDefaultMark(List<AssessmentEntity> assessmentEntities,String room) {
         List<UserEntity> students = enrolRepository.findStudentsBySection(room, ACADEMIC_YEAR, PageRequest.of(0,45))
                 .get()
                 .toList();
         if(students.isEmpty()){
             handleErrorResponse(request, response, new ApiException("student is not registered in this class"), HttpStatus.BAD_REQUEST);
             throw new ApiException("student is not registered in this class");
         }

         List<ResultEntity> results = new ArrayList<>();
         for(AssessmentEntity assessment: assessmentEntities){
             for(UserEntity student: students) {
                 results.add(ResultUtil.result(assessment,student,0));
             }
         }

         resultRepo.saveAll(results);
    }




}
