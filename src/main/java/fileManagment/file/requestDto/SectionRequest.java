package fileManagment.file.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionRequest {
    @NotEmpty(message = "grade can't be empty")
    private Integer grade;
    @NotEmpty(message = "field can't be empty")
    private Integer fieldCode;
    @NotEmpty(message = "room can't be empty")
    private Integer room;
    @NotEmpty(message = "block can't be empty")
    private Integer block;
}
