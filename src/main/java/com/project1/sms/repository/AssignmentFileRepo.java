package com.project1.sms.repository;

import com.project1.sms.model.AssignmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentFileRepo
        extends JpaRepository<AssignmentFile, Long> {

    List<AssignmentFile> findByAssignmentId(Long assignmentId);
}