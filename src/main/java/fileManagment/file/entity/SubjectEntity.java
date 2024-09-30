package fileManagment.file.entity;


import com.fasterxml.jackson.annotation.*;
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


    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("grade_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id",referencedColumnName ="id")
    private GradeEntity grade;


    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("field_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "subject_field",
            joinColumns = @JoinColumn(name =" subject_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "field_id",referencedColumnName = "id"))
    private FieldEntity field;
}
