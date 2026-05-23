package com.project1.sms.repository;

import com.project1.sms.model.CurrentSem;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentSemRepo extends JpaRepository<CurrentSem,Long> {
    Optional<CurrentSem> findTopByOrderByIdDesc();
}
