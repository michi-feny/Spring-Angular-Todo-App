package ibee.webapp.todo_app.validation;

import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class EqualToValidator implements ConstraintValidator<EqualTo, Object> {

    @Autowired
    private TranslationService translationService;

    private String field;
    private String fieldMatch;
    private String matchName;

    @Override
    public void initialize(EqualTo constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        String providedMatchName = constraintAnnotation.matchName();

        if(providedMatchName.startsWith("{") && providedMatchName.endsWith("}")) {
            String matchNameWithoutClamps = providedMatchName.substring(1, providedMatchName.length() - 1);
            String translatedMatchName = translationService.translate(matchNameWithoutClamps);
            this.matchName = translatedMatchName.equals(matchNameWithoutClamps + " is missing.") ? this.fieldMatch : translatedMatchName;
        }
        else {
            this.matchName = providedMatchName;
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        boolean isValid = (fieldValue != null && fieldValue.equals(fieldMatchValue))
                || (fieldValue == null && fieldMatchValue == null);

        if (!isValid) {

            context.disableDefaultConstraintViolation();

            HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);
            hibernateContext.addMessageParameter("matchName", this.matchName);

            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldMatch)
                    .addConstraintViolation();
        }

        return isValid;
    }
}