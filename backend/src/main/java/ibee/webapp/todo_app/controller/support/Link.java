package ibee.webapp.todo_app.controller.support;

import lombok.Builder;

@Builder
public record Link(
    String rel,
    String href,
    String method
) {}
