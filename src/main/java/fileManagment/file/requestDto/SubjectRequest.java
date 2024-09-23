package fileManagment.file.requestDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectRequest {
    @NotEmpty(message = "subject can't be empty")
    private String subject;
    @NotEmpty(message = "grade can't be empty")
    private Integer grade;
    private Integer field;
}
