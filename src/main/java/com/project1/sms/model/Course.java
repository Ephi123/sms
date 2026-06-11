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
@Builder
public class Course extends Auditable {

    @Column(nullable = false)
   private String courseName;
   @Column(unique = true,nullable = false)
   private String courseCode;

    @Column(nullable = false)
   private Integer creditHour;

   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

}
