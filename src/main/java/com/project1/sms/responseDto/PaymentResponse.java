package com.project1.sms.responseDto;

import com.project1.sms.model.Payment;

public record PaymentResponse(
        String fullName,
        String studentId,
        Integer academicYear,
        Integer sem,
        Integer month,
        Integer payment
) {
    public static PaymentResponse from(Payment payment){
        return new PaymentResponse(
           payment.getStudent().getUser().getFirstName()+" "+payment.getStudent().getUser().getMidlName(),
           payment.getStudent().getUser().getUserId(),
           payment.getAcademicYear(),
            payment.getSem(),
            payment.getMonth(),
                payment.getPayment()
        );
    }
}
