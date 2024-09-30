package fileManagment.file.repository;

import fileManagment.file.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepo extends JpaRepository<RegistrationEntity,String>{
}
