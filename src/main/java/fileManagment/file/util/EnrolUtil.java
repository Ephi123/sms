package fileManagment.file.util;

import fileManagment.file.entity.EnrolEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.UserEntity;

public class EnrolUtil {

    public static EnrolEntity createEnrol(UserEntity student , SectionEntity sec){
           var enrol = new EnrolEntity();
        enrol.addToSection(sec);
        enrol.addToStudent(student);
        enrol.addToGrades(sec.getGrade());
        enrol.setField(sec.getField());
        enrol.setIsActive(false);
        return enrol;
    }

}
