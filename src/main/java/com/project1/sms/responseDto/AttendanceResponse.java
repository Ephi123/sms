package com.project1.sms.responseDto;

public record AttendanceResponse(
        String studentId,
        String fullName,
        Long presentCount,
        Long absentCount,
        Long totalAttendance
) {
}
