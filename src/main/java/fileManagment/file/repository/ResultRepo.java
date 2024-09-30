package fileManagment.file.repository;

import fileManagment.file.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepo extends JpaRepository<ResultEntity,Long> {

}
