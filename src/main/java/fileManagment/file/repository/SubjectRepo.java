package fileManagment.file.repository;

import fileManagment.file.entity.SubjectEntity;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Long> {
    Optional<SubjectEntity> findBySubjectName(String subject);
}
