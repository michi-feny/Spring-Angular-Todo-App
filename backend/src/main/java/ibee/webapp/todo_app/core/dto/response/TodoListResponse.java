package ibee.webapp.todo_app.core.dto.response;

import ibee.webapp.todo_app.core.entity.Todo;

import java.util.List;

public record TodoListResponse(
    List<Todo> todos,
    Long totalTodos,
    Integer currentPage,
    Integer size,
    String sort
) {}