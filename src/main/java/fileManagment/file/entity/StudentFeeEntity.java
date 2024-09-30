package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_payments")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StudentFeeEntity extends Auditable {
    private Integer academicYear;
    private Integer monthFee;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id",referencedColumnName ="id")
    private GradeEntity grade;


}
