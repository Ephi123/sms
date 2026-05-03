package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project1.sms.enumeration.ProgramEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Program extends Auditable {
    @Column(nullable = false)
    private ProgramEnum name;
    @Column(nullable = false)
    private Integer semesterPerYear;

    @JsonManagedReference
    @OneToMany(mappedBy = "program", fetch = FetchType.LAZY)
    private List<Student> students;

}
