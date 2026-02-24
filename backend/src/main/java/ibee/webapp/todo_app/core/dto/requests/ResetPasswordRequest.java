package ibee.webapp.todo_app.core.dto.requests;

import ibee.webapp.todo_app.validation.EqualTo;
import ibee.webapp.todo_app.validation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@EqualTo(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "{validation.passwordConfirm}"
)
public record ResetPasswordRequest(
    @NotBlank
    @Password
    String password,
    @NotBlank
    String confirmPassword,
    @NotNull(message = "{validation.userId.provided}")
    Long userID,
    @NotBlank
    String token
) {}
