package com.project1.sms.Service.imp;

import com.project1.sms.Service.AttendanceService;
import com.project1.sms.apiException.ApiException;
import com.project1.sms.enumeration.AttendanceStatus;
import com.project1.sms.model.Attendance;
import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.Student;
import com.project1.sms.model.Teacher;
import com.project1.sms.repository.AttendanceRepo;
import com.project1.sms.repository.CourseOfferingRepo;
import com.project1.sms.repository.StudentRepo;
import com.project1.sms.repository.TeacherRepo;
import com.project1.sms.responseDto.AttendanceResponse;
import com.project1.sms.responseDto.StudentToAttendanceResponse;
import com.project1.sms.security.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class AttendanceIml implements AttendanceService {
    private final AttendanceRepo attendanceRepo;
    private final StudentRepo studentRepo;
    private final TeacherRepo teacherRepo;
    private final CurrentUserService userService;
    private final CourseOfferingRepo offeringRepo;

    //teacher,DepartmentHead,academicDean,vice,dean
    @Override
    public List<AttendanceResponse> getAttendanceReport(Long offeringId) {
        return attendanceRepo.getAttendanceReport(offeringId);
    }
     //teacher
    @Override
    public void createAttendance(Long offeringId, String studentId, int attendanceStatus) {
        Long teacherId = userService.getUserId();
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow(() -> new ApiException("teacher is not found"));
        Student student = studentRepo.findByUserUserId(studentId).orElseThrow(() -> new UsernameNotFoundException("student not found"));
        CourseOffering offering = offeringRepo.findById(offeringId).orElseThrow(() -> new ApiException("offering is not found"));
        AttendanceStatus status = attendanceStatus == 1? AttendanceStatus.PRESENT: AttendanceStatus.ABSENT;
        Attendance attendance =Attendance.builder()
                .offering(offering).
                teacher(teacher).
                student(student).
                Status(status).
                localDate(LocalDate.now()).build();
        attendanceRepo.save(attendance);



    }
       //teacher get students to create attendance
    @Override
    public List<StudentToAttendanceResponse> getStudentToAttendance(Long offeringId) {
        List<Student> students=attendanceRepo.getStudentsWithoutTodayAttendance(offeringId);
        return students.stream().
                map(StudentToAttendanceResponse::from).toList();
    }


}
