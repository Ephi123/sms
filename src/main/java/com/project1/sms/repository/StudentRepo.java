package com.project1.sms.repository;

import com.project1.sms.enumeration.StudentStatus;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    Optional<Student> findByUserUserId(String studentId);
    Optional<Student> findByUserPhone(String phone);

    @Query("""
       SELECT s
       FROM Student s
       WHERE s.createdAt >= :date
       AND s.currentYear = 1
       AND s.currentSem = 1
       AND s.studentStatus = :status
       """)
    List<Student> findUnenrolledStudents(
            LocalDateTime date,
            StudentStatus status
    );


}
