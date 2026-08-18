package ibee.webapp.todo_app.controller.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.jetbrains.annotations.Nullable;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public record ApiSuccessResponse<T> (
    @Nullable T data,
    @Nullable String message,
    @Nullable List<Link> links
) {
    public ApiSuccessResponse(T data, List<Link> links) {
        this(data, null, links);
    }
    public ApiSuccessResponse(T data, String msg) {
        this(data, msg, null);
    }

    public ApiSuccessResponse(String message, List<Link> links) {
        this(null, message, links);
    }
}