package fileManagment.file.service;

import fileManagment.file.entity.SubjectEntity;

public interface SubjectService {
    SubjectEntity saveSubject(String subject,int grade,int filed);
}
