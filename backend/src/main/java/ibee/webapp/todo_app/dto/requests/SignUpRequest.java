package ibee.webapp.todo_app.dto.requests;

import ibee.webapp.todo_app.validation.Choice;
import ibee.webapp.todo_app.validation.EqualTo;
import ibee.webapp.todo_app.validation.Password;
import jakarta.validation.constraints.*;

@EqualTo(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "{validation.passwordConfirm}"
)
public record SignUpRequest(
    @Size(min = 2, max = 30)
    @NotBlank
    String name,
    @NotBlank
    @Email
    String email,
    @NotBlank
    @Password
    String password,
    @NotBlank
    String confirmPassword,
    @NotBlank
    @Choice(choices = {"ROLE_USER", "ROLE_ADMIN"})
    String role
) {}
