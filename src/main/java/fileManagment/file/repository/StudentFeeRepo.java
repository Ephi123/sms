package fileManagment.file.repository;

import fileManagment.file.entity.StudentFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentFeeRepo extends JpaRepository<StudentFeeEntity,Long> {
    @Query("SELECT sf.monthFee FROM StudentFeeEntity sf " +
            "JOIN sf.grade g " +
            "WHERE g.grade = :grade AND sf.academicYear = :ac")
    Optional<Integer> findFeeByGrade(@Param("grade") int grade,@Param("ac") int academicYear);

}


