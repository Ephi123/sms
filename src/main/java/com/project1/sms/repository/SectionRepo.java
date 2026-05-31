package com.project1.sms.repository;

import com.project1.sms.model.Department;
import com.project1.sms.model.Program;
import com.project1.sms.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<Section,Long> {
    List<Section> findByDepartment(Department department);
    List<Section> findByProgram(Program program);
    Optional<Section> findBySection(Integer section);
    List<Section> findByDepartmentAndProgramOrderBySectionDesc(
            Department department,
            Program program
    );
}
