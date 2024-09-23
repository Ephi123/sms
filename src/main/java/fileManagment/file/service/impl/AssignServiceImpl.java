package fileManagment.file.service.impl;
import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.entity.AssignsEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.SubjectEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.enumeration.Authority;
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

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fileManagment.file.constant.Constant.ACADEMIC_YEAR;
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

            if(assignTeacherRepo.isTeacherAssigned(userId,sec,sub,ACADEMIC_YEAR)) {
                handleErrorResponse(request, response, new ApiException("Teacher is assigned"), HttpStatus.CONFLICT);
                throw new ApiException("Teacher is assigned");
            }

            SubjectEntity subject = getSubject(sub);
            SectionEntity section= getSection(sec);
            UserEntity teacher = getTeacher(userId);

            return assignTeacherRepo.save(createAssignEntity(subject,section,teacher,ACADEMIC_YEAR));

        }

        catch (Exception  e){
            handleErrorResponse(request, response,e,HttpStatus.BAD_REQUEST);
            throw e;
        }

      }

    @Override
    public List<UserEntity> freeTeachers(String sec) {
     List<UserEntity> allTeachers =  userRepo.findUsersByRole(Authority.TEACHER.name()).orElseThrow(() -> new ApiException("Teacher is not register"));
         Iterator<UserEntity> iterator = allTeachers.iterator();
     Optional<List<UserEntity>> assignedTeacher = assignTeacherRepo.findByTeacher(sec,ACADEMIC_YEAR);
     if(assignedTeacher.isEmpty())
           return allTeachers;

    while(iterator.hasNext()){
          UserEntity u = iterator.next();
          for(UserEntity u2: assignedTeacher.get())
                  if(Objects.equals(u.getUserId(),u2.getUserId()))
                      iterator.remove();

    }
    return allTeachers;
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
