package com.project1.sms.repository;

import com.project1.sms.dto.MonthlyPaymentReportDTO;
import com.project1.sms.model.Payment;
import com.project1.sms.model.Student;
import com.project1.sms.responseDto.FinanceOfficerPaymentSummaryResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {

    List<Payment> findByStudentAndSemAndAcademicYearOrderByMonthAsc(Student  student,Integer sem,Integer academicYear);
    @Query("""
SELECT new com.project1.sms.dto.MonthlyPaymentReportDTO(p.month,COUNT(p.student.user.userId),SUM(p.payment))
FROM Payment p WHERE p.academicYear =:year AND p.sem = :seme
GROUP BY p.month ORDER BY p.month

""")
    List<MonthlyPaymentReportDTO> getMonthlyReport(@Param("year") Integer year, @Param("seme") Integer sem);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("""
       UPDATE Payment p
       SET p.officerStatus = com.project1.sms.enumeration.FinaceOfficerStatus.CHECKED
       WHERE p.createdBy.id = :userId
       """)
    int updateOfficerStatusToChecked(@Param("userId") Long userId);


    @Query("""
       SELECT new com.project1.sms.dto.PaymentSummaryDTO(
           u.Id,
           CONCAT(u.firstName, ' ',u.midleName,' ', u.lastName),
           SUM(p.payment)
       )
       FROM Payment p
       JOIN UserEntity u ON u.Id = p.createdBy
       WHERE p.officerStatus =
           com.project1.sms.enumeration.FinanceOfficerStatus.PENDING
       GROUP BY u.Id, u.firstName, u.midleName, u.lastName
       """)

    List<FinanceOfficerPaymentSummaryResponse> getPendingPaymentSummary();


}
