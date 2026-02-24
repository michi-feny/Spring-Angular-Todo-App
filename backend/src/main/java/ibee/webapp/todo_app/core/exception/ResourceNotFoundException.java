package ibee.webapp.todo_app.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message, String i18nCode) {
        super(message, i18nCode);
    }

    public ResourceNotFoundException(String message, String i18nCode, Object... args) {
        super(message, i18nCode, args);
    }

    public ResourceNotFoundException(String message, String i18nCode, Throwable cause, Object... args) {
        super(message, i18nCode, cause, args);
    }
}
