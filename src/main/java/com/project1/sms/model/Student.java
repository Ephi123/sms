package com.project1.sms.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.StudentStatus;
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
public class Student extends Auditable {
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne()
    @JoinColumn(name = "student_id")
    private UserEntity user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private Integer currentYear;
    private Integer currentSem;

    private StudentStatus studentStatus;


}
