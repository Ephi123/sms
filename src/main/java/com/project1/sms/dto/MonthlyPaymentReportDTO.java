package com.project1.sms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyPaymentReportDTO {
    private int month;
    private long totalStudent;
    private double totalAmount;
    public MonthlyPaymentReportDTO(int month, long totalStudent, long totalAmount){
        this.month = month;
        this.totalAmount = totalAmount;
        this.totalStudent = totalStudent;

    }
}
