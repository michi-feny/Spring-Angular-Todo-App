package ibee.webapp.todo_app.core.exception;

public class RefreshTokenExpiredException extends RefreshTokenException {

    private static final String message = "Refresh token is expired. Please make a new sign-in request.";

    public RefreshTokenExpiredException() {
        super(message);
    }

    public RefreshTokenExpiredException(Throwable cause) {
        super(message, cause);
    }
}