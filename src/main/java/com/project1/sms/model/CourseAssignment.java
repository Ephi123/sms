package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class CourseAssignment extends Auditable{

 @OneToOne
 @JoinColumn(name = "course_offering_id")
 private CourseOffering courseOffering;

 @ManyToOne
 @JoinColumn(name = "teacher_id")
 private Teacher teacher;


}
