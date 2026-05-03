package com.project1.sms.repository;

import com.project1.sms.model.Department;
import com.project1.sms.model.PaymentScall;
import com.project1.sms.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentScallRepo extends JpaRepository<PaymentScall,Long> {
    Optional<PaymentScall> findByDepartment(Department department);

}
