package ibee.webapp.todo_app.infrastructure.email;

import ibee.webapp.todo_app.core.dto.EmailDetails;
import ibee.webapp.todo_app.core.ports.SendEmail;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SpringMailSender implements SendEmail {
    private final JavaMailSender javaMailSender;


    public boolean sendEmail(EmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setTo(emailDetails.to());
            helper.setSubject(emailDetails.subject());

            if (emailDetails.from() != null) {
                helper.setFrom(emailDetails.from());
            }
            helper.setText(emailDetails.body(), true);

            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            // Fehlerbehandlung und Logging
            return false;
        }
    }
}
