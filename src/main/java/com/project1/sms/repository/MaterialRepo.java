package com.project1.sms.repository;

import com.project1.sms.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepo extends  JpaRepository<Material,Long> {
    List<Material> findByTeacherIdOrderByPostedAtDesc(Long teacherId);
    List<Material> findByCourseOfferingIdOrderByPostedAtDesc(Long courseOfferingId);
}
