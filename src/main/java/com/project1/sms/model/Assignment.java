package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Assignment extends Auditable {
   private String title;
   private String Description;
   @ManyToOne
   @JoinColumn(name="offering_id")
   private CourseOffering  offering;
    @ManyToOne
    @JoinColumn(name="teacher_id")
   private Teacher teacher;
    private LocalDate dueDate;
}
