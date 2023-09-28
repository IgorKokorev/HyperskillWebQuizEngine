package engine.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterRequest {
    @Email(message = "Email is not valid", regexp="[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Size(min = 5)
    private String password;
}
