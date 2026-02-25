package ibee.webapp.todo_app.core.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTodo(
    @Size(min = 2, max = 100)
    @NotBlank
    String title,
    @Size(max = 500)
    @NotBlank
    String description
) {}
