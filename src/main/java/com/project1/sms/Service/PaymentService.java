package com.project1.sms.Service;

import java.sql.Struct;
import java.util.Map;

public interface PaymentService {
    Map<String, Object> getPaymentDetailOfStudent(String userId);
    Map<String,Object> makeFirstMonthFeeAndEnrollment(String studentId);
    Map<String,Object> makePaymentAfterEnroll(String studentId);
    Map<String,Object> monthlyReport();
}
