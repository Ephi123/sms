package fileManagment.file.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sections")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SectionEntity extends Auditable {
    private String room;
    private String block;
    private Integer studentNumber;
    @JsonIgnore
    private String qrCodeSecret;
    @Column(columnDefinition = "text")
    private String qrCodeImage;
    @ManyToOne
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
   @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("grade_id")
    private GradeEntity grade;


    @ManyToOne(fetch =FetchType.EAGER)
    @JoinTable(name = "section_field", joinColumns = @JoinColumn(name = "section_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "field_id",referencedColumnName = "id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("field_id")
    private FieldEntity field;

}
