package ibee.webapp.todo_app.core.result;

import java.util.List;

/**
 * Generic result of a service operation.
 *
 * A service operation can either:
 *
 * 1. SUCCEED
 *    -> value contains the resulting entity
 *
 * 2. BE REJECTED
 *    -> no exception is required because the request itself was
 *       syntactically valid, but the business operation was not accepted.
 *
 * 3. FAIL
 *    -> unexpected/technical problems should still use exceptions.
 *
 * The important distinction is:
 *
 * Business rejection != technical failure.
 */
public record ServiceResult<T>(
        Status status,
        T value,
        List<BusinessViolation> violations
) {

    public enum Status {
        SUCCESS,
        REJECTED
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public boolean isRejected() {
        return status == Status.REJECTED;
    }

    public static <T> ServiceResult<T> success(T value) {
        return new ServiceResult<>(
                Status.SUCCESS,
                value,
                List.of()
        );
    }

    public static <T> ServiceResult<T> rejected(
            BusinessViolation... violations
    ) {
        return new ServiceResult<>(
                Status.REJECTED,
                null,
                List.of(violations)
        );
    }
    public static <T> ServiceResult<T> rejected(T value, List<BusinessViolation> violations) {
        return new ServiceResult<>(Status.REJECTED, value, List.copyOf(violations));
    }

    public static <T> ServiceResult<T> rejected(
            List<BusinessViolation> violations
    ) {
        return new ServiceResult<>(
                Status.REJECTED,
                null,
                List.copyOf(violations)
        );
    }
}