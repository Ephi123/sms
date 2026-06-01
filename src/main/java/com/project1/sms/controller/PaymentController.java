package com.project1.sms.controller;

import com.project1.sms.Service.PaymentService;
import com.project1.sms.response.GlobalResponse;
import java.util.List;
import java.util.Map;

import com.project1.sms.responseDto.FinanceOfficerPaymentSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/students/{userId}")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> getPaymentDetailOfStudent(@PathVariable String userId) {
        Map<String,Object> paymentDetail = paymentService.getPaymentDetailOfStudent(userId);
        return ResponseEntity.ok(GlobalResponse.success("Student payment detail fetched successfully", paymentDetail));
    }

    @PostMapping("/students/{studentId}/first-month-fee-and-enrollment")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> makeFirstMonthFeeAndEnrollment(@PathVariable String studentId) {
        Map<String,Object> payment = paymentService.makeFirstMonthFeeAndEnrollment(studentId);
        return ResponseEntity.ok(GlobalResponse.success("First month fee and enrollment payment completed", payment));
    }

    @PostMapping("/students/{studentId}/after-enrollment")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> makePaymentAfterEnroll(@PathVariable String studentId) {
        Map<String,Object> payment = paymentService.makePaymentAfterEnroll(studentId);
        return ResponseEntity.ok(GlobalResponse.success("Payment after enrollment completed", payment));
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<GlobalResponse<Map<String,Object>>> monthlyReport() {
        Map<String,Object> report = paymentService.monthlyReport();
        return ResponseEntity.ok(GlobalResponse.success("Monthly payment report fetched successfully", report));
    }

    @PatchMapping("/finance-officers/{officerId}/status")
    public ResponseEntity<GlobalResponse<Void>> updateFinanceOfficerStatus(@PathVariable Long officerId) {
        paymentService.updateFinanceOfficerStatus(officerId);
        return ResponseEntity.ok(GlobalResponse.<Void>success("Finance officer status updated successfully", null));
    }

    @GetMapping("/finance-officers/pending")
    public ResponseEntity<GlobalResponse<List<FinanceOfficerPaymentSummaryResponse>>> getFinanceOfficerWithPendingStatus() {
        List<FinanceOfficerPaymentSummaryResponse>financeOfficers = paymentService.getFinanceOfficerWithPendingStatus();
        return ResponseEntity.ok(GlobalResponse.success("Pending finance officers fetched successfully", financeOfficers));
    }
}
