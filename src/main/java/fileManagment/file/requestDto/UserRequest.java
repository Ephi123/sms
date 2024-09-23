package fileManagment.file.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    @NotEmpty(message = "lastname can't be empty")
    private String lastName;
    @NotEmpty(message = "firstname can't be empty")
    private String firstName;
    @NotEmpty(message = "email can't be empty")
    @Email(message = "Invalid email address")
    private String email;
    private String bio;
    private String phone;
    private Integer age;
    private String gender;
    private String address;

}
