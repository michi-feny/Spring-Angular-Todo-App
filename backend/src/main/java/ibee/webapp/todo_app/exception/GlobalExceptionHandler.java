package ibee.webapp.todo_app.exception;

import ibee.webapp.todo_app.core.exception.BaseException;
import ibee.webapp.todo_app.core.exception.ResourceNotFoundException;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized global exception handler for the application.
 * Intercepts exceptions thrown by controllers or services and converts them 
 * into standardized, localized JSON responses using Spring 3 {@link ProblemDetail}.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final TranslationService translationService;


    public GlobalExceptionHandler(TranslationService translationService) {
        this.translationService = translationService;
    }

    /**
     * 1. Handles your custom BaseExceptions (like ResourceNotFoundException, ResetTokenException, etc.)
     * and automatically localizes the message using your TranslationService and i18n codes.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ProblemDetail> handleBaseException(BaseException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        // Translate the i18n code using dynamic arguments if provided
        String translatedMessage = 
            translationService.translate(
                ex.getI18nCode(), ex.getArgs());
        if (translatedMessage == null || translatedMessage.equals(ex.getI18nCode())) {
            translatedMessage = ex.getMessage(); // Fallback to debug message
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, translatedMessage);
        problemDetail.setTitle("Application Error");
        problemDetail.setType(URI.create("https://api.ihreapp.de/errors/" + ex.getI18nCode()));
        problemDetail.setProperty("timestamp", Instant.now());
       

        return ResponseEntity.status(status).body(problemDetail);
    }

    /**
     * 2. Handles Controller DTO validation failures (@Validated / @Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail(translationService.translate("validation.failed", "One or more fields have validation errors"));
        problemDetail.setType(URI.create("https://api.ihreapp.de/errors/validation-failed"));

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        problemDetail.setProperty("errors", fieldErrors);
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * 3. Handles Database Entity validation failures (Hibernate Pre-Persist/Pre-Update triggers)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Database Validation Failed");
        problemDetail.setDetail("Entity constraints were violated");
        problemDetail.setType(URI.create("https://api.ihreapp.de/errors/constraint-violation"));

        Map<String, String> violations = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = "";
            for (jakarta.validation.Path.Node node : violation.getPropertyPath()) {
                fieldName = node.getName();
            }
            violations.put(fieldName, violation.getMessage());
        }

        problemDetail.setProperty("errors", violations);
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Catches generic IllegalArgumentExceptions, commonly thrown by Spring's 
     * {@link org.springframework.util.Assert} class during validation.
     *
     * @param ex The intercepted IllegalArgumentException
     * @return A standard HTTP 400 Bad Request response containing the assertion message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, 
                ex.getMessage() // This will be the message you passed into Assert.notNull()
        );
        problemDetail.setTitle("Invalid Argument");
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * 4. Catch-all for any unhandled exceptions (Prevents ugly Tomcat 500 HTML pages)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
        // Log the actual error for the developer to see in the console
        ex.printStackTrace(); 
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "An unexpected server error occurred."
        );
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.ihreapp.de/errors/internal-error"));
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    /**
     * 5. Handles HTTP Method Not Supported (e.g., sending a GET request to a POST endpoint).
     * Provides a clear message to UI developers about what methods are actually allowed.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotSupportedException(
        HttpRequestMethodNotSupportedException ex
    ) {
        
        // Use 405 Method Not Allowed
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        
        // Build a highly descriptive message for the frontend developer
        String detailMessage = String.format(
            "The HTTP %s method is not supported for this URL. Supported methods are: %s", 
            ex.getMethod(), 
            ex.getSupportedHttpMethods()
        );

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detailMessage);
        problemDetail.setTitle("Method Not Allowed");
        problemDetail.setType(URI.create("https://api.ihreapp.de/errors/method-not-allowed"));
        problemDetail.setProperty("timestamp", Instant.now());
        
        // Optional: You can also use your translationService here if you want it localized!
        // problemDetail.setDetail(translationService.translate("error.methodNotAllowed", ...));


        return ResponseEntity.status(status).body(problemDetail);
    }

    // 2. Handles SPRING'S routing errors (URL missing / trailing slash)
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFoundException(org.springframework.web.servlet.resource.NoResourceFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                status, 
                "The requested URL path was not found on this server. Please check for typos or trailing slashes."
        );
        problemDetail.setTitle("Endpoint Not Found");
        // ... 
        return ResponseEntity.status(status).body(problemDetail);
    }
}
