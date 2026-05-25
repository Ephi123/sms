package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.FinaceOfficerStatus;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class Payment extends Auditable {

   @ManyToOne
   @JoinColumn(name = "userId")
    private Student student;
    private Integer academicYear;
    private Integer sem;
    private Integer month;
    private Integer payment;


}
