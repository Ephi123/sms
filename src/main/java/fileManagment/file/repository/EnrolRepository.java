package fileManagment.file.repository;

import fileManagment.file.entity.EnrolEntity;
import fileManagment.file.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrolRepository extends JpaRepository<EnrolEntity,Long> {

    @Query("SELECT COUNT(enrol) FROM EnrolEntity enrol " +
            "JOIN enrol.sections sec " +
            "WHERE enrol.academicYear= :ay AND sec.room = :rom")
    Integer contBySection(@Param("rom") String room, @Param("ay") int academicYear);

    @Query("SELECT stu FROM EnrolEntity enrol " +
            "JOIN enrol.students stu " +
            "JOIN enrol.sections sec " +
            "WHERE enrol.academicYear= :ay AND sec.room = :rom AND enrol.isActive = TRUE ")
    Page<UserEntity> findStudentsBySection(@Param("rom") String room, @Param("ay") int academicYear, Pageable pageable);


    @Query("SELECT g.grade FROM EnrolEntity enrol " +
            "JOIN enrol.students stu " +
            "JOIN enrol.grades g " +
            "WHERE enrol.academicYear = :ay AND stu.userId = :id")
    Optional<Integer> findGradeByStudentId(@Param("id") String userId, @Param("ay") int academicYear);
    List<EnrolEntity> findByIsActive(Boolean isActive);

    @Query("SELECT enrol FROM EnrolEntity enrol " +
            "JOIN enrol.sections sec "+
            "JOIN enrol.grades g " +
            "JOIN enrol.field f " +
            "JOIN enrol.students stu " +
            "WHERE enrol.academicYear = :ay AND stu.userId = :id")
    Optional<EnrolEntity> findByStudentId(@Param("id") String userId, @Param("ay") int academicYear);
 }
