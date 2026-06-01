package com.project1.sms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project1.sms.enumeration.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class Attendance extends Auditable{
    @ManyToOne
    @JoinColumn(name = "offering_id")
    private CourseOffering offering;
    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name="student_id")
   private  Student student;
    @Enumerated(EnumType.STRING)
   private  AttendanceStatus status;
    
    private LocalDate date;
}
