package com.project1.sms.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FinanceOfficerPaymentSummaryResponse {
    private Long userId;
    private String fullName;
    private Long totalPayment;
}
