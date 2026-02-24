package ibee.webapp.todo_app.core.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank
    String token
) {}
