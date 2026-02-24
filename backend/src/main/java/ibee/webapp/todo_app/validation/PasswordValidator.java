package ibee.webapp.todo_app.validation;

import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Autowired
    private TranslationService translationService;

    private String dynamicPattern;
    private Password annotation;

    @Override
    public void initialize(Password ann) {
        this.annotation = ann;

        StringBuilder patternBuilder = new StringBuilder("^");

        if (ann.requireNumber()) patternBuilder.append("(?=.*[0-9])");
        if (ann.requireLowerCase()) patternBuilder.append("(?=.*[a-z])");
        if (ann.requireUpperCase()) patternBuilder.append("(?=.*[A-Z])");
        if (ann.requireSpecialChar()) patternBuilder.append("(?=.*[@#$%^&+=!])");

        patternBuilder.append("(?=\\S+$)");
        patternBuilder.append(".{").append(ann.minLength()).append(",}$");

        this.dynamicPattern = patternBuilder.toString();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;

        if (!password.matches(dynamicPattern)) {
            context.disableDefaultConstraintViolation();

            String customMessage;
            String providedMessage = context.getDefaultConstraintMessageTemplate();

            if(providedMessage.isBlank()) {
                customMessage = buildDynamicMessage();
            }
            else if(providedMessage.startsWith("{") && providedMessage.endsWith("}")) {
                String providedMessageWithoutClamps = providedMessage.substring(1, providedMessage.length() - 1);
                String translatedCustomMmessage  = translationService.translate(providedMessageWithoutClamps);
                customMessage = translatedCustomMmessage.equals(providedMessageWithoutClamps + " is missing.")
                        ? buildDynamicMessage()
                        : translatedCustomMmessage;
            }
            else {
                customMessage = providedMessage;
            }

            context.buildConstraintViolationWithTemplate(customMessage)
                    .addConstraintViolation();

            return false;
        }

        return true;
    }

    private String buildDynamicMessage() {
        List<String> requirements = new ArrayList<>();

        requirements.add(
                translationService.translate(
                    "validation.password.min_length",
                    new Object[]{annotation.minLength()}
                )
        );

        if (annotation.requireNumber())
            requirements.add(translationService.translate("validation.password.require_number"));
        if (annotation.requireLowerCase())
            requirements.add(translationService.translate("validation.password.require_lowercase"));
        if (annotation.requireUpperCase())
            requirements.add(translationService.translate("validation.password.require_uppercase"));
        if (annotation.requireSpecialChar())
            requirements.add(translationService.translate("validation.password.require_special"));

        String prefix = translationService.translate("validation.password.prefix");
        String separator = ", ";

        return prefix + " " + String.join(separator, requirements) + ".";
    }
}