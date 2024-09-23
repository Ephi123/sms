package fileManagment.file.repository;

import fileManagment.file.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepo extends JpaRepository<GradeEntity,Long>{

    Optional<GradeEntity> findByGrade(Integer grade);
}
