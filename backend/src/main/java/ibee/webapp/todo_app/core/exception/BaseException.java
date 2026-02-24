package ibee.webapp.todo_app.core.exception;

public abstract class BaseException extends RuntimeException implements LocalizableException {
    private final String i18nCode;
    private final Object[] args;

    public BaseException(String debugMessage, String i18nCode, Throwable cause, Object... args) {
        super(debugMessage, cause);
        this.i18nCode = i18nCode;
        this.args = args;
    }

    public BaseException(String debugMessage, String i18nCode) {
        this(debugMessage, i18nCode, null, (Object[])null);
    }

    public BaseException(String debugMessage, String i18nCode, Object... args) {
        this(debugMessage, i18nCode, null, args);
    }

    @Override
    public String getI18nCode() {
        return i18nCode;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}
