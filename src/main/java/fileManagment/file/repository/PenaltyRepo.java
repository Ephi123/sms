package fileManagment.file.repository;

import fileManagment.file.entity.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepo extends JpaRepository<PenaltyEntity,Long> {

    PenaltyEntity findByPenaltyId(String penalty);
}
