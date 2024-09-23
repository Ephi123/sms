package fileManagment.file.service.impl;
import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.entity.AssignsEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.SubjectEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.repository.AssignTeacherRepo;
import fileManagment.file.repository.SectionRepo;
import fileManagment.file.repository.SubjectRepo;
import fileManagment.file.repository.UserRepo;
import fileManagment.file.service.AssignService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static fileManagment.file.util.AssignsUtil.createAssignEntity;
import static fileManagment.file.util.RequestUtil.handleErrorResponse;

@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Service
public class AssignServiceImpl implements AssignService {
    private final AssignTeacherRepo assignTeacherRepo;
    private final SubjectRepo subjectRepo;
    private final SectionRepo sectionRepo;
    private final UserRepo userRepo;
    private final HttpServletResponse response;
    private final HttpServletRequest  request;

    @Override
    @PreAuthorize("hasRole('USER')")
    public AssignsEntity assignTeacher(String userId,String sec,String sub) {
        try{
            int acYear = EthiopianCalendar.ethiopianYear();
            if(assignTeacherRepo.isTeacherAssigned(userId,sec,sub,acYear)) {
                handleErrorResponse(request, response, new ApiException("Teacher is assigned"), HttpStatus.CONFLICT);
                throw new ApiException("Teacher is assigned");
            }

            SubjectEntity subject = getSubject(sub);
            SectionEntity section= getSection(sec);
            UserEntity teacher = getTeacher(userId);

            return assignTeacherRepo.save(createAssignEntity(subject,section,teacher,acYear));

        }

        catch (Exception  e){
            handleErrorResponse(request, response,e,HttpStatus.BAD_REQUEST);
            throw e;
        }

      }

    private UserEntity getTeacher(String userId) {
        return userRepo.findByUserId(userId).orElseThrow(() -> new ApiException("user not found"));
    }

    private SectionEntity getSection(String sec) {
        return sectionRepo.findByRoom(sec).orElseThrow(() -> new ApiException("section is not found"));
    }

    private SubjectEntity getSubject(String sub) {
       return subjectRepo.findBySubjectName(sub).orElseThrow(() -> new ApiException("subject is not found"));
    }
}
