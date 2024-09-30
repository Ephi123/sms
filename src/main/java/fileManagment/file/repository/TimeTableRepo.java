package fileManagment.file.repository;

import fileManagment.file.entity.TimeTableEntity;
import fileManagment.file.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepo extends JpaRepository<TimeTableEntity,Long> {
    @Query("SELECT tt FROM TimeTableEntity tt " +
            "JOIN tt.day d " +
            "JOIN tt.period p " +
            "JOIN tt.teacher t " +
            "WHERE d.day = :day AND p.period = :period AND t.userId = :user AND tt.academicYear = :ay ")
    Optional<TimeTableEntity> isTeacherAssigned(@Param("day") String day, @Param("period") Integer period, @Param("user") String userName, @Param("ay") int accYear);


    @Query("SELECT t from  TimeTableEntity tt " +
            "JOIN tt.teacher t " +
            "JOIN tt.section s " +
            "WHERE s.room = :sec AND tt.academicYear = :ay")
    List<UserEntity> findTeacherBySection(@Param("sec") String room,@Param("ay") int academicYear);

}


