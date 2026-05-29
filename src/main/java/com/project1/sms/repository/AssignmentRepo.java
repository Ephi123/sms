package com.project1.sms.repository;

import com.project1.sms.model.Assessment;
import com.project1.sms.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment,Long> {
    @Query("""
       SELECT a
       FROM Assignment a
       WHERE a.dueDate >= CURRENT_TIMESTAMP AND
              a.offering.id = :offeringId
       """)
    List<Assignment> findActiveAssignments(  @Param("offeringId") Long offeringId );

    @Query("""
       SELECT a
       FROM Assignment a
       WHERE a.dueDate < CURRENT_TIMESTAMP AND
              a.offering.id = :offeringId
       """)
    List<Assignment> findAssignmentAfterSubmission(@Param("offeringId") Long offeringId);
}
