package fileManagment.file.repository;

import fileManagment.file.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepo extends JpaRepository<SemesterEntity,Long> {

    SemesterEntity findByIsCurrent(boolean current);
}
