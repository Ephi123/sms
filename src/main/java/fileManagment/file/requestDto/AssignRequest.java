package fileManagment.file.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString
public class AssignRequest {
    @NotEmpty(message = "subject can't be empty")
    private String subject;

    @NotEmpty(message = "section can't be empty")
    private String section;

    @NotEmpty(message = "teacher can't be empty")
    private String teacher;

}
