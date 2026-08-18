package ibee.webapp.todo_app.controller.support.hateoas.builder;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;

public final class ApiResponseBuilder {

    // Private constructor prevents instantiation
    private ApiResponseBuilder() {}

    /**
     * Wraps the data payload in the ApiSuccessResponse and sets the HTTP status.
     */
    public static <T> ResponseEntity<ApiSuccessResponse<T>> buildResponse(T body, String message, HttpStatus status) {
        ApiSuccessResponse<T> responseWrapper = new ApiSuccessResponse<>(body, message);
        return ResponseEntity.status(status).body(responseWrapper);
    }

    /**
     * Overloaded helper for default HTTP 200 OK responses.
     */
    public static <T> ResponseEntity<ApiSuccessResponse<T>> buildResponse(T body, String message) {
        return buildResponse(body, message, HttpStatus.OK);
    }

    /**
     * Helper for empty responses (like DELETE), returning null data but a success message.
     */
    public static ResponseEntity<ApiSuccessResponse<Void>> buildEmptyResponse(String message, HttpStatus status) {
        ApiSuccessResponse<Void> responseWrapper = new ApiSuccessResponse<>(null, message);
        return ResponseEntity.status(status).body(responseWrapper);
    }
}
