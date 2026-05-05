package com.project1.sms.repository;

import com.project1.sms.model.Assessment;
import com.project1.sms.model.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepo extends JpaRepository<Assessment,Long> {

    List<Assessment> findByCourseOffering(CourseOffering offering);

}
