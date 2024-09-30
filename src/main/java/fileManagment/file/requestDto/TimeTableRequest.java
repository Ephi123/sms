package fileManagment.file.requestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRequest {
    private Integer period;
    private String day;
    private String section;
    private String teacher;
}
