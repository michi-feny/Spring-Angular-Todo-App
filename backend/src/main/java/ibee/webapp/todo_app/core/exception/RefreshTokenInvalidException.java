package ibee.webapp.todo_app.core.exception;

public class RefreshTokenInvalidException extends RefreshTokenException {
    public RefreshTokenInvalidException(String message) {
        super(message);
    }
    public RefreshTokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}