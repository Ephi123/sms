package fileManagment.file.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fileManagment.file.domain.EthiopianCalendar.*;
import static java.time.LocalDateTime.*;

@Entity
@Table(name = "enrols")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EnrolEntity extends Auditable {

    private Integer academicYear;
    private Boolean isActive;
    private LocalDateTime date;
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "enrol_student", joinColumns = @JoinColumn(name = "enrol_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id") )
    private List<UserEntity> students;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "enrol_section", joinColumns = @JoinColumn(name = "enrol_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "section_id",referencedColumnName = "id"))
    private List<SectionEntity> sections;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "enrol_grade", joinColumns = @JoinColumn(name = "enrol_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "grade_id",referencedColumnName = "id"))
    private List<GradeEntity> grades;


    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "enrol_field", joinColumns = @JoinColumn(name = "enrol_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "field_id",referencedColumnName = "id"))
    private FieldEntity field;


    public void addToStudent(UserEntity student){
           students = new ArrayList<>();
        students.add(student);
    }
    public void addToSection(SectionEntity section){
        sections = new ArrayList<>();
        sections.add(section);
    }

    public void addToGrades(GradeEntity grade){
        grades = new ArrayList<>();
          grades.add(grade);
    }
    @PrePersist
    public void prePersist(){
        setAcademicYear(ethiopianYear());
        setDate(now());
    }

}
