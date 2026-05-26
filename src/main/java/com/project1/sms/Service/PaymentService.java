package com.project1.sms.Service;

import com.project1.sms.responseDto.FinanceOfficerPaymentSummaryResponse;

import java.sql.Struct;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    Map<String, Object> getPaymentDetailOfStudent(String userId);
    Map<String,Object> makeFirstMonthFeeAndEnrollment(String studentId);
    Map<String,Object> makePaymentAfterEnroll(String studentId);
    Map<String,Object> monthlyReport();
    void updateFinanceOfficerStatus(Long officerId);
     List<FinanceOfficerPaymentSummaryResponse> getFinanceOfficerWithPendingStatus();
}
