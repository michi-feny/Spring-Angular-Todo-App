package ibee.webapp.todo_app.infrastructure.email;

import ibee.webapp.todo_app.infrastructure.messaging.outbox.OutboxListener;
import ibee.webapp.todo_app.core.dto.EmailDetails;
import ibee.webapp.todo_app.core.event.PasswordResetTokenCreatedEvent;
import ibee.webapp.todo_app.core.ports.EmailContent;
import ibee.webapp.todo_app.core.ports.SendEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailEventHandler {
    private final SendEmail sendEmail;
    private final EmailContent emailContent;

    @Value("${spring.application.url}")
    private String URL;

    @OutboxListener
    public void onPasswordReset(PasswordResetTokenCreatedEvent event) {

        String htmlBody = emailContent.renderContent("password_reset", Map.of(
                "recipientEmail", event.recipientEmail(),
                "resetLink", URL + "auth/resetPassword?token=" + event.resetToken()
        ));

        EmailDetails resetEmail = new EmailDetails(
                event.recipientEmail(),
                "Ihr Link zur Passwort-Zurücksetzung",
                htmlBody,
                "security@company.com"
        );

        if (!sendEmail.sendEmail(resetEmail)) {
            throw new RuntimeException("E-Mail-Zustellung fehlgeschlagen.");
        }
    }
}
