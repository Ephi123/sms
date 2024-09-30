package fileManagment.file.repository;

import fileManagment.file.entity.AssignsEntity;
import fileManagment.file.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignTeacherRepo extends JpaRepository<AssignsEntity,Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END " +
            "FROM AssignsEntity a " +
            "JOIN a.section sec " +
            "JOIN a.subject sub " +
            "JOIN a.teacher t " +
            "WHERE sec.room = :sec AND t.userId = :id AND sub.subjectName = :sub AND a.academicYear = :ay ")
    Boolean isTeacherAssigned(@Param("id") String userId, @Param("sec") String sec, @Param("sub") String subject, @Param("ay") int accYear);

    @Query("SELECT a FROM AssignsEntity a " +
            "JOIN a.section sec " +
            "WHERE sec.room = :sec AND a.academicYear = :ay")
    List<AssignsEntity> findBySection(@Param("sec") String sec,@Param("ay") Integer accYear);

    @Query("SELECT t FROM AssignsEntity a " +
            "JOIN a.section sec " +
            "JOIN a.teacher t " +
            "WHERE sec.room = :sec AND a.academicYear = :ay")
    Optional<List<UserEntity>> findTeacherBySectionAndAy(@Param("sec") String sec, @Param("ay") Integer accYear);


}