package fileManagment.file.repository;

import fileManagment.file.entity.NextPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NextPaymentRepo extends JpaRepository<NextPaymentEntity,Long> {
    @Query("SELECT np.month FROM NextPaymentEntity np " +
            "JOIN np.student S " +
            "WHERE S.userId = :stuId ")
    Optional<Integer> findByStudentId(@Param("stuId") String studentId);

    @Query("SELECT np FROM NextPaymentEntity np " +
            "JOIN np.student S " +
            "WHERE S.userId = :stuId ")
    Optional<NextPaymentEntity> findNextPaymentByStudentId(@Param("stuId") String studentId);

}
