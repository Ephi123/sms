package fileManagment.file.requestDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Setter
@Getter
@ToString
public class LoginDto {
    @NotEmpty(message = "email can't be empty")
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty(message = "password can't be empty")
    private String password;

}
