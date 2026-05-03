package com.project1.sms.repository;

import com.project1.sms.enumeration.ProgramEnum;
import com.project1.sms.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramRepo extends JpaRepository<Program,Long> {


    Optional<Program> findByName(ProgramEnum programName);
}
