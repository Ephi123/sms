package com.project1.sms.repository;

import com.project1.sms.enumeration.FinanceOfficerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceAuditRepo extends JpaRepository<FinaceAudit,Long> {
    List<FinaceAudit> findByFinaceOfficerStatus(FinanceOfficerStatus status);
}
