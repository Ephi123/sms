package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.entity.DayEntity;
import fileManagment.file.entity.PeriodEntity;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.UserEntity;
import fileManagment.file.repository.*;
import fileManagment.file.service.TimeTableService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static fileManagment.file.constant.Constant.ACADEMIC_YEAR;
import static fileManagment.file.util.TimeTableUtil.timeTableGenerator;
import static org.springframework.http.HttpStatus.*;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {
    private final TimeTableRepo timeTableRepo;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final SectionRepo sectionRepo;
    private final UserRepo userRepo;
    private final PeriodRepo periodRepo;
    private final DayRepo dayRepo;
    private final AssignTeacherRepo assignTeacherRepo;

    @Override
    @PreAuthorize("hasRole('USER')")
    public void addToTimeTable(String day, int period, String teacherId, String sec) {
        var res = timeTableRepo.isTeacherAssigned(day,period,teacherId, ACADEMIC_YEAR);
        if(res.isPresent()){
            RequestUtil.handleErrorResponse(request,response,new ApiException(" Teacher "+  res.get().getTeacher().getFirstName() + " is assigned to "+ res.get().getSection().getRoom()+" on "+res.get().getDay().getDay() +" at period "+res.get().getPeriod().getPeriod()), BAD_REQUEST);
            throw new ApiException("teacher is already assigned");
        }
        timeTableRepo.save(timeTableGenerator(day(day),period(period),teacher(teacherId),section(sec)));

    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<UserEntity> freeTeachers(String sec) {
          return assignTeacherRepo.findTeacherBySectionAndAy(sec, ACADEMIC_YEAR).orElseThrow(() ->{
            RequestUtil.handleErrorResponse(request,response,new ApiException("section is not assigned to ay teacher"), BAD_REQUEST);
            return new ApiException("section is not assigned to ay teacher");
        });
    }

    private SectionEntity section(String sec) {
        return sectionRepo.findByRoom(sec).orElseThrow(() ->{
            RequestUtil.handleErrorResponse(request,response,new ApiException("section is not found"), BAD_REQUEST);
            return new ApiException("section is not found");
        });
    }

    private UserEntity teacher(String teacherId) {
        return userRepo.findByUserId(teacherId).orElseThrow(() ->{
            RequestUtil.handleErrorResponse(request,response,new ApiException("Teacher is not found"), BAD_REQUEST);
            return new ApiException("Teacher is not found");
        });
    }

    private PeriodEntity period(int period) {
        return periodRepo.findByPeriod(period).orElseThrow(() ->{
            RequestUtil.handleErrorResponse(request,response,new ApiException("Period is not found"), BAD_REQUEST);
            return new ApiException("Period is not found");
        });

    }

    private DayEntity day(String day) {
      return dayRepo.findByDay(day).orElseThrow(() ->{
          RequestUtil.handleErrorResponse(request,response,new ApiException("Day is not found"), BAD_REQUEST);
          return new ApiException("Day is not found");
      });
    }
}
