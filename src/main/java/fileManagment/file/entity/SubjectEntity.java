package fileManagment.file.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SubjectEntity extends Auditable{
    private String subjectName;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id",referencedColumnName ="id")
    private GradeEntity grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "subject_field",
            joinColumns = @JoinColumn(name =" subject_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "field_id",referencedColumnName = "id"))
    private FieldEntity field;
}
