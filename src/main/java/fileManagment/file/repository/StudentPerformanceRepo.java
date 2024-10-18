package fileManagment.file.repository;

import fileManagment.file.entity.StudentPerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPerformanceRepo extends JpaRepository<StudentPerformanceEntity,Long> {

}
