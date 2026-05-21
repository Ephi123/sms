package com.project1.sms.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewStudentResponse {
    String fullName;
    String department;
    String program;
    int academicYear;
    double registrationFee;
    double monthFee;
    double total;
}
