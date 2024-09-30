package fileManagment.file.repository;

import fileManagment.file.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PeriodRepo extends JpaRepository<PeriodEntity,Long> {
    Optional<PeriodEntity> findByPeriod(Integer period);
}
