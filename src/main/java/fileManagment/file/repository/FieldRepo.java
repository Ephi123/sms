package fileManagment.file.repository;

import fileManagment.file.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepo extends JpaRepository<FieldEntity,Long>{

    Optional<FieldEntity> findByFieldCode(Integer fieldCode);
}