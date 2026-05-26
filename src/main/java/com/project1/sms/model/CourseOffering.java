package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class CourseOffering extends Auditable{

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    private Integer studyYear;
    private Integer sem;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private Integer academicYear;
    @OneToMany(mappedBy = "courseOffering")
    private List<Material> materials = new ArrayList<>();
}
