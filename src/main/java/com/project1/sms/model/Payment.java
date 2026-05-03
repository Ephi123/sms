package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Payment extends Auditable {

   @ManyToOne
   @JoinColumn(name = "userId")
    private Student student;
    private Integer academicYear;
    private Integer sem;
    private Integer month;
    private Integer payment;

}
