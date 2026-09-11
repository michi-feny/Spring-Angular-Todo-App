package ibee.webapp.todo_app.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record ValidatePasswordResetRequest(
    @NotBlank
    String token
) {}
