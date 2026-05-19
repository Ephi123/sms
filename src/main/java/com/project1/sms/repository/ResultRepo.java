package com.project1.sms.repository;

import com.project1.sms.model.Result;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepo extends JpaRepository<Result,Long> {

    Result findByStudent(Student student);

    Optional<Result> findByStudentAndAcademicYearAndSemester(
            Student student,
            Integer academicYear,
            Integer semester
    );

    List<Result> findByStudentOrderByAcademicYearAscSemesterAsc(Student student);

}
