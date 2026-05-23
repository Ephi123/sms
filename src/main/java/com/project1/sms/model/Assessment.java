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
public class Assessment extends Auditable{
    @ManyToOne
    @JoinColumn(name = "course_offering_id")
    private CourseOffering courseOffering;
    private String title;
    private Integer WeightPercent;
}
