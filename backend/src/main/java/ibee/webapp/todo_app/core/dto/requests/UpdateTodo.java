package ibee.webapp.todo_app.core.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTodo(
    @NotNull(message = "{validation.todoId.provided")
    Long id,
    @NotBlank
    @Size(min = 2, max = 100)
    String title,
    @Size(max = 500)
    String description,
    Boolean isDone
) {}
