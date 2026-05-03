package com.project1.sms.repository;

import com.project1.sms.dto.MonthlyPaymentReportDTO;
import com.project1.sms.model.Payment;
import com.project1.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {

    Optional<Payment> findByStudentAndSemAndAcademicYear(Student  student,Integer sem,Integer academicYear);
    @Query("""
SELECT new com.project1.sms.dto.MonthlyPaymentReportDTO(p.month,COUNT(p.student.user.userId),SUM(p.payment))
FROM Payment p WHERE p.academicYear =:year AND p.sem = :seme
GROUP BY p.month ORDER BY p.month

""")
    List<MonthlyPaymentReportDTO> getMonthlyReport(@Param("year") Integer year, @Param("seme") Integer sem);
}
