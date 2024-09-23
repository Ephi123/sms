package fileManagment.file.repository;

import fileManagment.file.entity.EnrolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolRepository extends JpaRepository<EnrolEntity,Long> {

    @Query("SELECT COUNT(enrol) FROM EnrolEntity enrol " +
            "JOIN enrol.sections sec " +
            "WHERE enrol.academicYear= :ay AND sec.room = :rom")
    Integer contBySection(@Param("rom") String room, @Param("ay") Integer academicYear);
}
