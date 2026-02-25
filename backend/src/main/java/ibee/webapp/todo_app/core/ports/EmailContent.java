package ibee.webapp.todo_app.core.ports;

import java.util.Map;

public interface EmailContent {
    String renderContent(String templateName, Map<String, Object> context);
}