package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.ResultStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Result extends Auditable{

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    private Integer semester;
    private Integer academicYear;
    private Integer studyYear;
    private BigDecimal gpa;
    private BigDecimal cgpa;
    private ResultStatus status;
    @Column(nullable = false)
    private Integer semesterCreditHours;

    @Column(nullable = false)
    private Integer cumulativeCreditHours;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private UserEntity user;


}
