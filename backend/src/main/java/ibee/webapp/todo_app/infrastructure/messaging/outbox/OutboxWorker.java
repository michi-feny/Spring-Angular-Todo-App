package ibee.webapp.todo_app.infrastructure.messaging.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import ibee.webapp.todo_app.core.entity.OutBoxMessage;
import ibee.webapp.todo_app.core.event.DomainEvent;
import ibee.webapp.todo_app.core.repository.OutboxMessageRepository;
import ibee.webapp.todo_app.core.ports.MessageDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OutboxWorker {
    private final MessageDispatcher messageDispatcher;
    private final OutboxMessageRepository repository;
    private final ObjectMapper objectMapper;
    private static final int MAX_RETRIES = 3;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void processSingleMessage(Long id) throws Exception {

        OutBoxMessage message = repository.findByIdWithLock(id)
                .orElseThrow(() -> new RuntimeException("Message nicht gefunden: " + id));

        if (message.isProcessed() || message.isFailedPermanently()) return;

        try {
            Class<?> eventClass = Class.forName(message.getType());
            DomainEvent event = (DomainEvent) objectMapper.readValue(message.getContent(), eventClass);

            OutboxContext.setRunning(true);

            messageDispatcher.dispatch(event);

            message.setProcessed(true);
            message.setProcessedOn(LocalDateTime.now());
            message.setErrorMessage(null);
            repository.save(message);
        } catch (ClassNotFoundException | JsonProcessingException e) {
            message.setFailedPermanently(true);
            message.setErrorMessage("Kritischer Serialisierungsfehler: " + e.getMessage());
            repository.save(message);
            throw e;
        } finally {
            OutboxContext.setRunning(false);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFailureAndRetry(Long id, String error) {
        repository.findById(id).ifPresent(message -> {

            if (message.isProcessed()) return;

            message.setRetryCount(message.getRetryCount() + 1);
            message.setErrorMessage(error);

            if (message.getRetryCount() >= MAX_RETRIES) {
                message.setFailedPermanently(true);
            } else {
                long waitSeconds = (long) Math.pow(2, message.getRetryCount());
                message.setNextAttempt(LocalDateTime.now().plusSeconds(waitSeconds));
            }
            repository.save(message);
        });
    }
}
