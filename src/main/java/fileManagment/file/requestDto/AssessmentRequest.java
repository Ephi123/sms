package fileManagment.file.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentRequest {
    private Long id;
    private String assessmentName;
    private Integer wight;
    private String subject;
    private String section;
    private String teacher;
}
