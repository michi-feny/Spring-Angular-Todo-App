package ibee.webapp.todo_app.core.exception;

public class UserNotFoundException extends ResourceNotFoundException{
    public UserNotFoundException(String message, String i18nCode) {
        super(message, i18nCode);
    }
}
