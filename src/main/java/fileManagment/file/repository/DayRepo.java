package fileManagment.file.repository;

import fileManagment.file.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DayRepo extends JpaRepository<DayEntity,Long> {
    Optional<DayEntity> findByDay(String day);
}
