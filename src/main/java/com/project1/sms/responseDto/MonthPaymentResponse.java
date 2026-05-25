package com.project1.sms.responseDto;

import com.project1.sms.model.Payment;


public record MonthPaymentResponse(
        String message
) {
    public static MonthPaymentResponse from(Payment payment){

        return new MonthPaymentResponse(
                "Month"+payment.getMonth() +" Payed"
        );
    }
}
