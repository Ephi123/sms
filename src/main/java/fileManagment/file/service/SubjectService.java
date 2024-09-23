package fileManagment.file.service;

import fileManagment.file.entity.SubjectEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SubjectService {
    SubjectEntity saveSubject(String subject,int grade,int filed);
    List<SubjectEntity> getSubjectByGrade(int grade);
    Page<SubjectEntity> getAllSubject(int size, int page);
}
