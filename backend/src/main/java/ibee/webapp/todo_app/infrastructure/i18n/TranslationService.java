package ibee.webapp.todo_app.infrastructure.i18n;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TranslationService {

    private final MessageSource messageSource;
    private static final Object[] EMPTY_ARGS = new Object[0];

    private String translate(String key, String defaultMessage, Object... args) {
        Locale currentLocale = LocaleContextHolder.getLocale();

        String translatedMessage = messageSource.getMessage(key, args, defaultMessage, currentLocale);

        return Objects.requireNonNull(translatedMessage,
                "MessageSource returned null unexpectedly for key: " + key);
    }

    public String translate(String key, @Nullable String customDefaultMessage) {

        String fallback = customDefaultMessage != null ? customDefaultMessage : key + " is missing.";

        return translate(key, fallback, EMPTY_ARGS);
    }

    public String translate(String key) {
        String genericFallback = key + " is missing.";
        return translate(key, genericFallback, EMPTY_ARGS);
    }

    public String translate(String key, Object... args) {
        String genericFallback = key + " is missing.";
        return translate(key, genericFallback, args);
    }
}
