package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_status")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SubjectStatusEntity extends Auditable{
    private Integer academicYear;
    private Integer sem;
    private String status;
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonProperty("field_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id",referencedColumnName = "id")
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id",referencedColumnName = "id")
    private SectionEntity section;
}
