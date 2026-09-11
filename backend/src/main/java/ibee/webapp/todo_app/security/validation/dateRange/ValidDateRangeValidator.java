package ibee.webapp.todo_app.security.validation.dateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

import org.springframework.util.Assert;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        
        try {
            LocalDate startDate = extractDate(value, startField);
            LocalDate endDate = extractDate(value, endField);

            // Null checks are handled by @NotNull on the fields themselves
            if (startDate == null && endDate == null) {
                return false;
            }
            
            LocalDate now = LocalDate.now();

            if (startDate != null && endDate == null) {
                
                if(
                   isBeforeOrEqualToPresent(startDate, now)
                )
                    return true;
            }
            boolean isValid = false;

            if(startDate != null && endDate != null){
                isValid = 
                    isBeforeOrEqualToPresent(startDate, now)
                    && 
                    isBeforeOrEqualToPresent(endDate, now);
                
                if(isValid == false)
                    return false;
            }

            isValid = (endDate.isBefore(startDate) == true)
                        ? false:true;

            // Bind the error directly to the endField so the frontend knows exactly which input box to highlight red
            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                       .addPropertyNode(endField) 
                       .addConstraintViolation();
            }

            return isValid;

        } catch (Exception e) {
            // Fails safe if a developer typos the field name in the annotation
            throw new IllegalArgumentException(
                "Could not validate date range. Are you sure the fields '" + startField + "' and '" + endField + "' exist?", e);
        }
    }

    private LocalDate extractDate(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // Grants access to private fields and record properties
        Object date = field.get(obj);
        return date == null ? null : (LocalDate) date;
    }

    private boolean isBeforeOrEqualToPresent(LocalDate dateToCheck, LocalDate currentDate){
        LocalDate [] dates = new LocalDate[2];
        dates[0] = dateToCheck;
        dates[1] = currentDate;

        Assert.noNullElements(
            dates, 
            "Dates that should be checked are not allowed to be both null!");
                
        if(
            dateToCheck.isEqual(currentDate) 
                    || 
            dateToCheck.isBefore(currentDate)
        )
            return true;
        return false;

    }
}
