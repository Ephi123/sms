package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AssessmentEntity extends Auditable {
    private String assessmentName;
    private Integer wight;
    private Integer academicYear;
    private Integer semester;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("subject_id")
    @ManyToOne
    @JoinColumn(name = "subject_id",referencedColumnName ="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubjectEntity subject;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("section_id")
    @ManyToOne
    @JoinColumn(name = "section_id",referencedColumnName ="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SectionEntity section;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("teacher_id")
    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName ="id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity teacher;


}
