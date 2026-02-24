package ibee.webapp.todo_app.core.exception;


public class ResetTokenException extends BaseException {
    public ResetTokenException() {
        super("The token is invalid or has already been used. Check that you have copied the entire link.", "reset.tokenInvalid");
    }

    public ResetTokenException(String message, String i18nCode) {
        super(message, i18nCode);
    }
}
