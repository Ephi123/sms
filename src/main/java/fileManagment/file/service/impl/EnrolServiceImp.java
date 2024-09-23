package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.entity.EnrolEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.repository.EnrolRepository;
import fileManagment.file.repository.SectionRepo;
import fileManagment.file.service.EnrolService;
import fileManagment.file.service.UserService;
import fileManagment.file.util.EnrolUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class EnrolServiceImp implements EnrolService {
    private final UserService userService;
    private final SectionRepo sectionRepo;
    private final EnrolRepository enrolRepository;

    @Override
    @PreAuthorize("hasRole('USER')")
    public EnrolEntity register(String firstName, String lastName, String email, String authority, Integer age, String address, String gender, String userId, Integer grade, Integer field) {
        UserEntity userEntity = userService.createUser(firstName, lastName, email, authority, age, address, gender, userId);
        var enrolEntity = EnrolUtil.createEnrol(userEntity,sectionEntity(grade,field));
             return enrolRepository.save(enrolEntity);

    }


    private SectionEntity sectionEntity(Integer grade1, Integer field1) {
        List<SectionEntity> sectionEntities = sectionRepo.findByGradeAndField(grade1, field1).orElseThrow(() -> new ApiException("section not found"));

        return freeSection(sectionEntities);

    }

    private SectionEntity freeSection(List<SectionEntity> sectionEntities) {

        return sectionEntities.stream().filter(section -> section.getStudentNumber() >= enrolRepository.contBySection(section.getRoom(), EthiopianCalendar.ethiopianYear())).findAny().orElseThrow(() -> new ApiException("section is not found"));

    }
}