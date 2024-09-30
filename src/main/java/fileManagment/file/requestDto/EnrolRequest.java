package fileManagment.file.requestDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrolRequest extends UserRequest {
    @NotEmpty(message = "grade can't be empty")
    private Integer grade;
    @NotEmpty(message = "field can't be empty")
    private Integer field;

}
