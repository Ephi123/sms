package fileManagment.file.repository;

import fileManagment.file.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CredentialRepo extends JpaRepository<CredentialEntity,Long> {

    Optional<CredentialEntity> getCredentialByUserEntityId(Long userTd);
}
