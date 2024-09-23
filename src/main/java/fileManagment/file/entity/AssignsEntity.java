package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import fileManagment.file.domain.EthiopianCalendar;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assigns")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AssignsEntity extends Auditable{

    private Integer academicYear;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id",referencedColumnName ="id")
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id",referencedColumnName ="id")
    private SectionEntity section;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id",referencedColumnName ="id")
    private UserEntity teacher;

    @PrePersist
    public void beforePersist(){
        setAcademicYear(EthiopianCalendar.ethiopianYear());
    }

}
