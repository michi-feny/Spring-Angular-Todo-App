package ibee.webapp.todo_app.core.exception;

public class TodoNotFoundException extends ResourceNotFoundException {
    public TodoNotFoundException(Long id) {
        super("Couldn't find todo with ID: " + id, "todo.notFound", id);
    }
}
