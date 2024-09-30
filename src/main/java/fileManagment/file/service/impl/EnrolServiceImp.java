package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.entity.*;
import fileManagment.file.repository.*;
import fileManagment.file.service.EnrolService;
import fileManagment.file.service.UserService;
import fileManagment.file.util.EnrolUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fileManagment.file.constant.Constant.*;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class EnrolServiceImp implements EnrolService {
    private final UserService userService;
    private final SectionRepo sectionRepo;
    private final EnrolRepository enrolRepository;
    private final NextPaymentRepo nextPaymentRepo;
    private final RegistrationRepo registrationRepo;
    private final StudentFeeRepo studentFeeRepo;

    @Override
    @PreAuthorize("hasRole('USER')")
    public EnrolEntity register(String firstName, String lastName, String email, String authority, Integer age, String address, String gender, String userId, Integer grade, Integer field) {
        UserEntity userEntity = userService.createUser(firstName, lastName, email, authority, age, address, gender, userId);
        var enrolEntity = EnrolUtil.createEnrol(userEntity,sectionEntity(grade,field));
        var nextPayment = NextPaymentEntity.builder().month(0).student(userEntity).build();
             nextPaymentRepo.save(nextPayment);
             return enrolRepository.save(enrolEntity);

    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<Map<?,?>> getRegistrationFee() {
        var enrols = enrolRepository.findByIsActive(false);
          List<Map<?,?>> list = new ArrayList<>();
        for(EnrolEntity enrol: enrols){
             var grade = enrol.getGrades().getFirst();
             var payment = studentFeeRepo.findFeeByGrade(grade.getGrade(), ACADEMIC_YEAR).orElseThrow(() -> new ApiException("payment is not find"));
             var registration = registrationRepo.findById("reg").orElseThrow(() -> new ApiException("registrationFee not found"));
             var student = enrol.getStudents().getFirst();
             list.add(Map.of("Fee",payment,
                     "Registration Fee", registration.getRegistrationFee(),
                    "FirstName",student.getFirstName(),
                      "Father Name",student.getLastName(),
                     "userId",student.getUserId(),
                     "grade",grade.getGrade()));
        }
  return list;
    }


    private SectionEntity sectionEntity(Integer grade1, Integer field1) {
        List<SectionEntity> sectionEntities = sectionRepo.findByGradeAndField(grade1, field1).orElseThrow(() -> new ApiException("section not found"));
        System.out.println(sectionEntities);
        return freeSection(sectionEntities);

    }

    private SectionEntity freeSection(List<SectionEntity> sectionEntities) {
        if(sectionEntities.isEmpty()){
            throw new ApiException("section is not registered in this Grade");
        }

        return sectionEntities.stream().filter(section -> section.getStudentNumber() >= enrolRepository.contBySection(section.getRoom(), ACADEMIC_YEAR) ).findFirst().orElseThrow(() -> new ApiException("all registered Sections are full"));

    }
}