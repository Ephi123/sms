package fileManagment.file.service;

import fileManagment.file.entity.AssessmentEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.SubjectEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.requestDto.AssessmentRequest;

import java.util.List;

public interface AssessmentService {
    void crateDefaultAssessment(SubjectEntity sub, SectionEntity sec, UserEntity teacher);
    void updateAssessment(AssessmentRequest assessmentRequest,String subject,String sec);
    void deleteAssessment(Long id);
    void addAssessment(AssessmentRequest assessmentRequest);
    List<AssessmentEntity> getAssessments(String sec,String sub);
}
