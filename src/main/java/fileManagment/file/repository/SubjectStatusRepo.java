package fileManagment.file.repository;

import fileManagment.file.entity.SubjectStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectStatusRepo extends JpaRepository<SubjectStatusEntity,Long> {




    @Query("SELECT s FROM SubjectStatusEntity s " +
            "JOIN section sec " +
            "JOIN subject sub " +
            "WHERE s.academicYear = :ay " +
            "AND sec.room = :sec " +
            "AND s.status = :status")
    List<SubjectStatusEntity> findApprovedSubject(@Param("ay") int year, @Param("sec") String section, @Param("status") String status);

    @Query("SELECT s FROM  SubjectStatusEntity s " +
            "JOIN section sec " +
            "JOIN subject sub " +
            "WHERE s.academicYear = :ay " +
            "AND s. sem = :sem " +
            "AND sec.room = :sec " +
            "AND sub.subjectName = :sub")
    SubjectStatusEntity findSubjectEntityBySecSubSem(@Param("ay") int year,@Param("sem") int sem,
                                                     @Param("sec") String section,
                                                     @Param("sub") String subject);
    List<SubjectStatusEntity> findByStatus(String status);




}
