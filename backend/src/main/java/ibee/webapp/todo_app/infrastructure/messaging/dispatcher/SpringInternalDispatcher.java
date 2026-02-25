package ibee.webapp.todo_app.infrastructure.messaging.dispatcher;

import ibee.webapp.todo_app.core.event.DomainEvent;
import ibee.webapp.todo_app.core.ports.MessageDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class SpringInternalDispatcher implements MessageDispatcher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void dispatch(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}