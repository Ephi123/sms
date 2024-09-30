package fileManagment.file.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer Fee;

}
