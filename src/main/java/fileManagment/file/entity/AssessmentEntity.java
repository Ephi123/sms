package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id",referencedColumnName ="id")
    private SubjectEntity subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id",referencedColumnName ="id")
    private SectionEntity section;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "semester_id",referencedColumnName ="id")
    private SemesterEntity semester;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id",referencedColumnName ="id")
    private UserEntity teacher;


}
