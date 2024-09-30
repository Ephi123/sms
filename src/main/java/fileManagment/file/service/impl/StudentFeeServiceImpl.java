package fileManagment.file.service.impl;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.entity.GradeEntity;
import fileManagment.file.repository.GradeRepo;
import fileManagment.file.repository.StudentFeeRepo;
import fileManagment.file.service.StudentFeeService;
import fileManagment.file.util.PaymentUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static fileManagment.file.util.PaymentUtil.*;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class StudentFeeServiceImpl implements StudentFeeService {
    private final GradeRepo gradeRepo;
    private final StudentFeeRepo studentFeeRepo;
    @Override
    @PreAuthorize("hasRole('USER')")
    public void definePayment(int grade, int fee) {

        studentFeeRepo.save(generateStudentFee(grade(grade),fee));
    }

    private GradeEntity grade(int grade) {
        return gradeRepo.findByGrade(grade).orElseThrow(() -> new ApiException("grade is not created"));
    }
}
