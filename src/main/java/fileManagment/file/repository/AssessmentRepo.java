package fileManagment.file.repository;
import fileManagment.file.entity.AssessmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentRepo extends JpaRepository<AssessmentEntity,Long> {
    @Query("SELECT a FROM AssessmentEntity a " +
            "JOIN a.section sec " +
            "JOIN a.subject sub " +
            "WHERE sec.room = :sec AND sub.subjectName = :sub AND a.academicYear = :ay " +
            "ORDER BY a.wight ASC")
    List<AssessmentEntity> findAssessmentsBySection(@Param("sec") String sec, @Param("ay") int accYear, @Param("sub") String subject);
}
