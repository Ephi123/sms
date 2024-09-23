package fileManagment.file.util;

import fileManagment.file.entity.FieldEntity;
import fileManagment.file.entity.GradeEntity;
import fileManagment.file.entity.SubjectEntity;

public class SubjectUtil {
   public static SubjectEntity createSubjectEntity(String subject, GradeEntity grade1, FieldEntity field1){
     return SubjectEntity.builder()
             .subjectName(subject)
             .grade(grade1)
             .field(field1)
             .build();
   }
}
