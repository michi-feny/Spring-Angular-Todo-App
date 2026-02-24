package ibee.webapp.todo_app.core.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
    @NotBlank
    @Email
    String email
) {}
