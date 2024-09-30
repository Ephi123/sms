package fileManagment.file.util;

import fileManagment.file.entity.AssessmentEntity;
import fileManagment.file.entity.ResultEntity;
import fileManagment.file.entity.UserEntity;

public class ResultUtil {
    public static ResultEntity result(AssessmentEntity assessment, UserEntity student,int mark){
        return ResultEntity.builder()
                .assessment(assessment)
                .student(student)
                .mark(mark)
                .build();
    }
}
