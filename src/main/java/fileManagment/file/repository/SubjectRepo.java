package fileManagment.file.repository;

import fileManagment.file.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Long> {
    Optional<SubjectEntity> findBySubjectName(String subject);
}
