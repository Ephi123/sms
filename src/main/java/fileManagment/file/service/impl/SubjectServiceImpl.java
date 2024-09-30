package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.entity.FieldEntity;
import fileManagment.file.entity.GradeEntity;
import fileManagment.file.entity.SubjectEntity;
import fileManagment.file.repository.FieldRepo;
import fileManagment.file.repository.GradeRepo;
import fileManagment.file.repository.SubjectRepo;
import fileManagment.file.service.SubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;

import static fileManagment.file.util.SubjectUtil.createSubjectEntity;


@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepo subjectRepo;
    private final GradeRepo gradeRepo;
    private final FieldRepo fieldRepo;
    @Override
    @PreAuthorize("hasRole('USER')")
    public void saveSubject(String subject, int grade, int filed) {
        var  gradeEntity = getGrade(grade);
        var fieldEntity= getField(filed);
        subjectRepo.save(createSubjectEntity(subjectName.apply(subject, gradeEntity), gradeEntity, fieldEntity));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<SubjectEntity> getSubjectByGrade(int grade) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Page<SubjectEntity> getAllSubject(int page, int size) {
        return  subjectRepo.findAll(PageRequest.of(page,size));
    }

    private GradeEntity getGrade(int grade) {
        return gradeRepo.findByGrade(grade).orElseThrow(() -> new ApiException("grade not found "));

    }

    private FieldEntity getField(int filed) {
       return fieldRepo.findByFieldCode(filed).orElseThrow(() -> new ApiException("field not found"));
    }
 private final static BiFunction<String,GradeEntity,String> subjectName = (subject, grade) -> subject +" Grade " + grade.getGrade();

}
