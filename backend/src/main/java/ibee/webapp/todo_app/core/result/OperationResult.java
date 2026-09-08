package ibee.webapp.todo_app.core.result;

import java.util.List;

public sealed interface OperationResult<T>
        permits OperationResult.Success,
                OperationResult.Rejected {

    record Success<T>(
        T value
    ) implements OperationResult<T> {
    }

    record Rejected<T>(
        List<BusinessViolation> violations
    ) implements OperationResult<T> {

        public Rejected {
            violations = List.copyOf(violations);
        }
    }

    static <T> Success<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Rejected<T> rejected(
            List<BusinessViolation> violations) {

        return new Rejected<>(violations);
    }
}
