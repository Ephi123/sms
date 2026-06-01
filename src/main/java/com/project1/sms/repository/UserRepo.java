package com.project1.sms.repository;

import com.project1.sms.enumeration.Role;
import com.project1.sms.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByPhone(String string);
    Boolean existsByUserName(String userName);
    @Query("""
       SELECT u
       FROM UserEntity u
       WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(u.midlName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :search, '%'))
       OR u.phone LIKE CONCAT('%', :search, '%')
       """)
    Page<UserEntity> searchUsers(
            @Param("search") String search,
            Pageable pageable
    );

    Optional<UserEntity> findByUserId(String userId);
    @Query(value = """
  SELECT *
    FROM user u
    WHERE CONCAT(',', u.roles, ',')
          LIKE CONCAT('%,', :roleName, ',%')
    ORDER BY u.user_name ASC
""", nativeQuery = true)
    List<UserEntity> findByRole(@Param("roleName") String roleName);
    @Query(value = """
SELECT *
FROM user u
WHERE CONCAT(',', u.roles, ',') LIKE '%,TEACHER,%'
AND NOT EXISTS (
    SELECT 1
    FROM teacher t
    WHERE t.teacher_id = u.id
)
""", nativeQuery = true)
    List<UserEntity> getUnregisteredTeachers();

}
