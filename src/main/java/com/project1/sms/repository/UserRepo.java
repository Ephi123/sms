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
    List<UserEntity> findByRoleOrderByUsernameAtAsc(Role role);

    @Query("""
SELECT u FROM UserEntity u
WHERE com.project1.sms.enumeration.Role.TEACHER MEMBER OF u.roles
AND NOT EXISTS (
    SELECT t FROM Teacher t
    WHERE t.user = u
)
""")
    List<UserEntity> getUnregisteredTeachers();

}
