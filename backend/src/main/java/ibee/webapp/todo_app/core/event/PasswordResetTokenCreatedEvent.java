package ibee.webapp.todo_app.core.event;

public record PasswordResetTokenCreatedEvent(
        String recipientEmail,
        String resetToken
) implements DomainEvent {}