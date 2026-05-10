package com.project1.sms.repository;

import com.project1.sms.model.CourseOffering;
import com.project1.sms.model.CourseStaus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseStatusRepo extends JpaRepository<CourseStaus,Long> {

    Optional<CourseStaus> findByOffering(CourseOffering offering);

}
