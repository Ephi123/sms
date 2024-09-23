package fileManagment.file.repository;

import fileManagment.file.entity.ConfirmationEntity;
import fileManagment.file.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ConfirmationRepo extends JpaRepository<ConfirmationEntity,Long> {

    Optional<ConfirmationEntity> findByToken(String token);
    Optional<ConfirmationEntity> findByUserEntity(UserEntity userEntity);
}
