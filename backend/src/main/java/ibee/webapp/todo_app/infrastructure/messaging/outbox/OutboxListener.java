package ibee.webapp.todo_app.infrastructure.messaging.outbox;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventListener
public @interface OutboxListener {
    @AliasFor(annotation = EventListener.class, attribute = "condition")
    String condition() default "T(ibee.webapp.todo_app.infrastructure.messaging.outbox.OutboxContext).isActive()";
}