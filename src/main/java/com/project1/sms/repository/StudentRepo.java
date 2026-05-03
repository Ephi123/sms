package com.project1.sms.repository;

import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {

    Optional<Student> findByUserUserId(String studentId);
    Optional<Student> findByUserPhone(String phone);


}
