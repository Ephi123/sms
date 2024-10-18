package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "student_prformannce")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StudentPerformanceEntity extends Auditable{
    private String userId;
    private String fName;
    private String lName;
    private String sec;
    private Integer sem;
    private Integer year;
    private Double average;
    private Long std_rank;
    private Double cumulativeAvg;
    private Long cumulativeRank;
    private Long rankFromAll;
}
