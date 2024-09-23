package fileManagment.file.repository;

import fileManagment.file.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Long> {
    boolean existsBySubjectName(String subject);
}
