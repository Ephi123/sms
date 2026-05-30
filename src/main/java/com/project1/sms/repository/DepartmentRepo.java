package com.project1.sms.repository;

import com.project1.sms.model.Department;
import com.project1.sms.model.Teacher;
import com.project1.sms.responseDto.DepartmentWithHeadResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long> {

    Optional<Department> findByDepName(String name);
    Optional<Department> findByHead(Teacher teacher);
    List<Department> findByHeadIsNotNullOrderByDepNameAsc();
    List<Department> findByHeadIsNullOrderByDepNameAsc();

    @Query("""
    SELECT new com.project1.sms.responseDto.DepartmentWithHeadResponse(
        d.id,
        d.depName,
        t.id,
        CONCAT(
            COALESCE(u.firstName, ''),
            ' ',
            COALESCE(u.middleName, '')
        )
    )
    FROM Department d
    JOIN d.head t
    JOIN t.user u
    ORDER BY d.depName ASC
    """)
    List<DepartmentWithHeadResponse> findAllDepartmentsWithHead();
}
