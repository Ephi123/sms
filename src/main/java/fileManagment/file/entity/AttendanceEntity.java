package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Entity
@Table(name = "attendances")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(value = {"date"},allowGetters = true)
public class AttendanceEntity extends Auditable {

    private Integer academicYear;
    private LocalDateTime date;
    private Boolean isPreset;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "semester_id",referencedColumnName ="id")
    private SemesterEntity semester;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id",referencedColumnName ="id")
    private UserEntity student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id ",referencedColumnName ="id")
    private SectionEntity section;

   @PrePersist
    void dateBeforePersist(){
        setDate(now());
    }


}
