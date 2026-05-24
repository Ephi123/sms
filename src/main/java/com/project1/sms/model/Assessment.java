package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "assessment",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AssessmentResult> assessmentResults = new ArrayList<>();

    public Assessment(CourseOffering offering, String title, Integer weight) {
        super();
    }
}
