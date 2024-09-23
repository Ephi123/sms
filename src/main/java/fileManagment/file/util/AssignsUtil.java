package fileManagment.file.util;

import fileManagment.file.entity.AssignsEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.SubjectEntity;
import fileManagment.file.entity.UserEntity;

public class AssignsUtil {
    public static AssignsEntity createAssignEntity(SubjectEntity subject, SectionEntity  section, UserEntity teacher,int ay){
        return AssignsEntity.builder()
                .teacher(teacher)
                .section(section)
                .subject(subject)
                .academicYear(ay)
                .build();
    }
}
