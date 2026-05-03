package com.project1.sms.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Course extends Auditable {
   private String courseName;
   @Column(unique = true,nullable = false)
   private String courseCode;
   private Integer creditHour;

   @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
