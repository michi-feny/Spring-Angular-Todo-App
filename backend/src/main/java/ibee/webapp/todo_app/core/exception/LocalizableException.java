package ibee.webapp.todo_app.core.exception;

public interface LocalizableException {
    String getI18nCode();
    default Object[] getArgs() {
        return null;
    }
}
