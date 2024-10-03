package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResultEntity extends Auditable {
    private Double mark;
    private String status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assessment_id",referencedColumnName ="id")
    private AssessmentEntity assessment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id",referencedColumnName ="id")
    private UserEntity student;

}
