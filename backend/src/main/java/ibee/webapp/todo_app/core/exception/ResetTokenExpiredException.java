package ibee.webapp.todo_app.core.exception;

public class ResetTokenExpiredException extends ResetTokenException {
    public ResetTokenExpiredException() {
        super("The provided Reset Token has expired. Please request a new reset link.", "reset.tokenExpired");
    }
}
