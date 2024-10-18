package fileManagment.file.repository;
import fileManagment.file.entity.SectionEntity;
import fileManagment.file.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepo extends JpaRepository<SectionEntity,Long> {

    Optional<SectionEntity> findByRoom(String room);

    @Query("SELECT s FROM SectionEntity s " +
            "JOIN s.grade g " +
            "JOIN s.field f " +
            "WHERE g.grade = :grade AND f.fieldCode = :filedCode")
    Optional<List<SectionEntity>> findByGradeAndField(@Param("grade") Integer grade,@Param("filedCode") Integer field);

}