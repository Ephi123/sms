package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "time_tables")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TimeTableEntity extends Auditable{
    private Integer academic_year;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "period_id",referencedColumnName ="id")
    private PeriodEntity period;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id",referencedColumnName ="id")
    private DayEntity day;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id",referencedColumnName ="id")
    private SectionEntity section;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id",referencedColumnName ="id")
    private UserEntity teacher;

}
