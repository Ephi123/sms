package com.project1.sms.repository;

import com.project1.sms.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long> {

    Optional<Department>findByDepName(String name);
}
