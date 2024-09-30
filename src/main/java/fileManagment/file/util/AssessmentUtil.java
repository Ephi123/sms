package fileManagment.file.util;

import fileManagment.file.constant.Constant;
import fileManagment.file.entity.*;
import fileManagment.file.repository.SemesterRepo;
import fileManagment.file.requestDto.AssessmentRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
public class AssessmentUtil {
    public static  List<AssessmentEntity> createDefaultAssessments(SubjectEntity sub, SectionEntity sec, UserEntity teacher, SemesterEntity semester){

     return List.of(
             assessment(sub,sec,teacher,semester,"Mid Exam 1",15),
             assessment(sub,sec,teacher,semester,"Mid Exam 2",15),
             assessment(sub,sec,teacher,semester,"Assignment",20),
             assessment(sub,sec,teacher,semester,"Fina Exam",50)

     );

    }

    public static  AssessmentEntity assessment(SubjectEntity sub, SectionEntity sec, UserEntity teacher, SemesterEntity semester, String assessmentName, int wight){
        return AssessmentEntity.builder()
                .assessmentName(assessmentName)
                .wight(wight)
                .section(sec)
                .academicYear(Constant.ACADEMIC_YEAR)
                .subject(sub)
                .teacher(teacher)
                .semester(semester)
                .build();
    }

    public static AssessmentEntity update(AssessmentRequest assessmentRequest,AssessmentEntity assessment){

        if(assessmentRequest.getAssessmentName() != null){
            assessment.setAssessmentName(assessmentRequest.getAssessmentName());
        }
        if(assessmentRequest.getWight() !=null ){
            assessment.setWight(assessmentRequest.getWight());
        }

        return assessment;

    }

}
