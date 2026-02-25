package ibee.webapp.todo_app.infrastructure.email;

import ibee.webapp.todo_app.core.ports.EmailContent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.Locale;
import java.util.Map;

@Component
@AllArgsConstructor
public class EmailTemplateRenderer implements EmailContent {
    private final SpringTemplateEngine templateEngine;

    @Override
    public String renderContent(String templateName, Map<String, Object> context) {

        Context thymeleafContext = new Context(Locale.GERMAN);
        thymeleafContext.setVariables(context);

        return templateEngine.process("emails/" + templateName, thymeleafContext);
    }
}
