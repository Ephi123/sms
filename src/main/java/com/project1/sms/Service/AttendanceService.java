package com.project1.sms.Service;

import com.project1.sms.responseDto.AttendanceResponse;
import com.project1.sms.responseDto.StudentToAttendanceResponse;

import java.util.List;

public interface AttendanceService {

    List<AttendanceResponse> getAttendanceReport(Long offeringId);
    void createAttendance(Long offeringId,String studentId,int attendanceStatus);
    List<StudentToAttendanceResponse> getStudentToAttendance(Long offeringId);
}
