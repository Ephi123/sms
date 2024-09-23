package fileManagment.file.repository;

import fileManagment.file.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);
    Optional<UserEntity> findByUserId(String userId);
    boolean existsByEmail(String email);

    Optional<List<UserEntity>> findByRoles(String role);
}
