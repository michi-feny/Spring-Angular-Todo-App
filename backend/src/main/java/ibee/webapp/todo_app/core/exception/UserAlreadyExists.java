package ibee.webapp.todo_app.core.exception;

public class UserAlreadyExists extends BaseException {
    public UserAlreadyExists(String value, String type) {
        super("User with " + type + value + " already exists.", "user.alreadyExists", value);
    }
}
