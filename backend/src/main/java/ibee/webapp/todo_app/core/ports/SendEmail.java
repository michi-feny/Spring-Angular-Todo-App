package ibee.webapp.todo_app.core.ports;

import ibee.webapp.todo_app.core.dto.EmailDetails;

public interface SendEmail {
    boolean sendEmail(EmailDetails emailDetails);
}