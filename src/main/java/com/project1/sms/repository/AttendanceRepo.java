package com.project1.sms.repository;

import com.project1.sms.enumeration.AttendanceStatus;
import com.project1.sms.model.Attendance;
import com.project1.sms.model.Student;
import com.project1.sms.responseDto.AttendanceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface  AttendanceRepo extends JpaRepository<Attendance,Long> {
    @Query("""
    SELECT new com.project1.sms.responseDto.AttendanceResponse(
        s.user.userId,
        CONCAT(s.user.firstName, ' ', s.user.midlName, ' ', s.user.lastName),

        SUM(CASE 
                 WHEN a.status = :present
                THEN 1 
                ELSE 0 
            END),

        SUM(CASE 
                WHEN a.status = :absent
                THEN 1 
                ELSE 0 
            END),

        COUNT(a)
    )
    FROM Attendance a
    JOIN a.student s
    WHERE a.offering.id = :offeringId
    GROUP BY s.user.userId, s.user.firstName, s.user.midlName, s.user.lastName
""")
    List<AttendanceResponse> getAttendanceReport(Long offeringId, @Param("present") AttendanceStatus present, @Param("absent") AttendanceStatus absent);

    @Query("""
    SELECT e.student
    FROM Enrollment e
    WHERE e.courseOffering.id = :offeringId
    AND NOT EXISTS (
        SELECT a.id
        FROM Attendance a
        WHERE a.student = e.student
        AND a.offering = e.courseOffering
        AND a.date = CURRENT_DATE
    )
""")
    List<Student> getStudentsWithoutTodayAttendance(Long offeringId);
}
