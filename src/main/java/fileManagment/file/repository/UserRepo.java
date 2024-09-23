package fileManagment.file.repository;

import fileManagment.file.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);
    Optional<UserEntity> findByUserId(String userId);
    boolean existsByEmail(String email);
   @Query("SELECT u FROM UserEntity u " +
           "JOIN u.roles r " +
           "WHERE r.name = :role ")
   Optional<List<UserEntity>> findUsersByRole(@Param("role") String role);
    @Query("SELECT u FROM UserEntity u " +
            "JOIN u.roles r " +
            "WHERE r.name = :role ")
   Page<UserEntity> findUsersByRolePageable(@Param("role") String role, Pageable  pageable);
}
