package ibee.webapp.todo_app.infrastructure.messaging.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibee.webapp.todo_app.core.entity.OutBoxMessage;
import ibee.webapp.todo_app.core.event.DomainEvent;
import ibee.webapp.todo_app.core.repository.OutboxMessageRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxMessageRepository repository;
    private final ObjectMapper objectMapper;

    @EventListener(condition = "!T(ibee.webapp.todo_app.infrastructure.messaging.outbox.OutboxContext).isActive()")
    public void handleEvent(DomainEvent domainEvent) {
        try {
            String eventType = domainEvent.getClass().getName();
            String content = objectMapper.writeValueAsString(domainEvent);

            OutBoxMessage message = new OutBoxMessage();
            message.setType(eventType);
            message.setContent(content);

            repository.save(message);
        } catch (Exception e) {
            throw new RuntimeException("Serialisierung oder Outbox-Speichern fehlgeschlagen. Transaktion wird zurückgerollt.", e);
        }
    }
}