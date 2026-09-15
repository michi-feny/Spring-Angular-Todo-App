package ibee.webapp.todo_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChoiceValidator implements ConstraintValidator<Choice, String> {
    private List<String> choices;

    @Override
    public void initialize(Choice annotation) {
        choices = new ArrayList<>();

        if (annotation.enumClass() != Choice.AnyEnum.class) {
            choices = Arrays.stream(annotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }
        else {
            choices = Arrays.asList(annotation.choices());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return choices.contains(value);
    }
}