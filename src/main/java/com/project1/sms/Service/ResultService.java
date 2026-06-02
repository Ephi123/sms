package com.project1.sms.Service;

import com.project1.sms.dto.SemesterResultDto;

import java.math.BigDecimal;
import java.util.List;

public interface ResultService {
    SemesterResultDto calculateSemesterResult(String studentId, Integer academicYear, Integer semester);
    SemesterResultDto calculateSemesterResult();

    List<SemesterResultDto> calculateAllSemesterResults(String studentId);
    List<SemesterResultDto> calculateAllSemesterResults();

    BigDecimal calculateCgpa(String studentId);

    SemesterResultDto recalculateAndSaveSemesterResult(String studentId);

}
