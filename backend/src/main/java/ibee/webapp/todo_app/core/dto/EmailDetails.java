package ibee.webapp.todo_app.core.dto;

public record EmailDetails(
    String to,
    String subject,
    String body,
    String from
) {}