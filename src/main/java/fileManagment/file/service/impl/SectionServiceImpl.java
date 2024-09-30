package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.entity.FieldEntity;
import fileManagment.file.entity.GradeEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.repository.FieldRepo;
import fileManagment.file.repository.GradeRepo;
import fileManagment.file.repository.SectionRepo;
import fileManagment.file.service.SectionService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService {
    private final SectionRepo sectionRepo;
    private final GradeRepo gradeRepo;
    private final FieldRepo fieldRepo;
    private final HttpServletResponse response;
    private final HttpServletRequest request;


    @Override
    @PreAuthorize("hasRole('USER')")
    public SectionEntity createSection(Integer room, Integer block, Integer grade, Integer fieldCode,Integer studentNumber) {
        var section =  createSectionEntity(withRoomPrefix.apply(room),withBlockPrefix.apply(block),grade, fieldCode,studentNumber);

        return sectionRepo.save(section);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Page<SectionEntity> findsSections(int page, int size) {
        return sectionRepo.findAll(PageRequest.of(page,size));
    }

    @Override
    public SectionEntity findSection(String room) {
        if(sectionPresent(room)){
            return sectionRepo.findByRoom(room).get();
        }
       throw new  ApiException("section is not found");
    }

    private boolean sectionPresent(String room) {
        return sectionRepo.findByRoom(room).isPresent();

    }
     @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    private SectionEntity createSectionEntity(String room, String block, Integer grade, Integer fieldCode,Integer studentNumber) {
           if (sectionPresent(room)){
               RequestUtil.handleErrorResponse(request,response,new ApiException("Room is already registered"), HttpStatus.BAD_REQUEST);
               throw new ApiException("Room is already registered");
           }

        return SectionEntity.builder().block(block).room(room).
                studentNumber(studentNumber)
                .grade(getGrade(grade))
                 .field(getField(fieldCode))
                 .build();

    }

    private FieldEntity getField(Integer fieldCode) {
        Optional<FieldEntity> field =fieldRepo.findByFieldCode(fieldCode);
        if(field.isPresent())
            return  field.get();
        RequestUtil.handleErrorResponse(request,response,new ApiException("filed is not found"), HttpStatus.NOT_FOUND);
        throw new ApiException("filed is not found");
    }

    private GradeEntity getGrade(Integer grade) {
        Optional<GradeEntity> grade1 =gradeRepo.findByGrade(grade);
        if(grade1.isPresent())
            return   grade1.get();
        RequestUtil.handleErrorResponse(request,response,new ApiException("grade is not found"), HttpStatus.NOT_FOUND);
        throw new ApiException("grade is not found");

    }

private static final Function<Integer,String> withRoomPrefix = integer -> "ROOM-"+integer;
    private static final Function<Integer,String> withBlockPrefix = integer -> "BLOCK-"+integer;

}