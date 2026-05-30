package com.project1.sms.repository;

import com.project1.sms.model.Department;
import com.project1.sms.model.Teacher;
import com.project1.sms.responseDto.TeacherWithDepartmentDTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByUserUserId(String teacherId);
    List<Teacher> findByDepartment(Department department);
    Optional<Teacher> findByUserId(Long userId);

    @Query("""
          SELECT new om.project1.sms.responseDto.TeacherWithDepartmentDTo(
                      t.id,
                      CONCAT(t.user.firstName,t.user.midleName),
                      t.user.phone,
                      t.department.depName,
                      t.user.isActive)
        FROM Teacher t
      """)
    List<TeacherWithDepartmentDTo> getAllTeacherWithDepartment();
}
