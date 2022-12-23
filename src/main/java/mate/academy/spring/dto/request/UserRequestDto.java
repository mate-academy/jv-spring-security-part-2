package mate.academy.spring.dto.request;

import javax.validation.constraints.Size;
import lombok.Getter;
import mate.academy.spring.lib.FieldsValueMatch;
import mate.academy.spring.lib.ValidEmail;

@Getter
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRequestDto {
    @ValidEmail
    private String email;
    @Size(min = 8, max = 40)
    private String password;

    private String repeatPassword;
}
