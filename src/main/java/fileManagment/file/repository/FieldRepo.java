package fileManagment.file.repository;

import fileManagment.file.entity.FieldEntity;
import fileManagment.file.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepo extends JpaRepository<FieldEntity,Long>{

    Optional<FieldEntity> findByFieldCode(Integer fieldCode);

    @Query("SELECT e.grades From EnrolEntity e " +
            "Join students st " +
            "WHERE st.userId = :userId " +
            "AND e.academicYear = :year")
    GradeEntity findGradeByUserId(@Param("userId") String userId,@Param("year") int year);
}