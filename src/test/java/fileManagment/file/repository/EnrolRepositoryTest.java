package fileManagment.file.repository;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.domain.RequestContext;
import fileManagment.file.entity.EnrolEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Transactional(rollbackOn = Exception.class)
@SpringBootTest
class EnrolRepositoryTest {
    @Autowired
    private EnrolRepository enrolRepository;
    @Autowired
    private GradeRepo gradeRepo;
    @Autowired
    private SectionRepo sectionRepo;
    @Autowired
    private FieldRepo fieldRepo;
    @Test
    void addToEnrol(){
        RequestContext.setUserId(0L);
        var sec= sectionRepo.findByRoom("ROOM-1").orElseThrow(() -> new ApiException("room is not found"));
        var grade = sec.getGrade();
        System.out.println(grade);
        var field = sec.getField();

        var enrol = new EnrolEntity();
           enrol.setAcademicYear(EthiopianCalendar.ethiopianYear());
           enrol.setIsValid(true);
           enrol.setField(field);
           enrol.addToGrades(grade);
           enrol.addToSection(sec);

           EnrolEntity enrolEntity = enrolRepository.save(enrol);
        System.out.println(enrolEntity);



    }

}