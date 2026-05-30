package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

public class Department extends Auditable {
    @NotNull
    @Column(unique = true, nullable = false)
    private String depName;

    @OneToOne
    @JoinColumn(name = "head_id")
    private Teacher head;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Teacher> teachers = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();


}
