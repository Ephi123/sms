package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.FinaceOfficerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FinaceAudit extends Auditable{
    @Enumerated(EnumType.STRING)
    private FinaceOfficerStatus finaceOfficerStatus;

    @ManyToOne
    @JoinColumn(name = "finace_officer_id")
    private UserEntity finaceOfficer;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal collectedMoney=  BigDecimal.ZERO;;


}
