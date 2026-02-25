package ibee.webapp.todo_app.infrastructure.messaging.outbox;

import ibee.webapp.todo_app.core.entity.OutBoxMessage;
import ibee.webapp.todo_app.core.repository.OutboxMessageRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageScheduler {

    private final OutboxMessageRepository repository;
    private final OutboxWorker worker;
    private static final int BATCH_SIZE = 30;

    @Scheduled(fixedDelay = 30000)
    public void processPendingMessages() {

        boolean hasMore;

        do {
            List<Long> ids = repository.findPendingIds(
                    LocalDateTime.now(),
                    PageRequest.of(0, BATCH_SIZE)
            );

            for (Long id : ids) {
                try {
                    worker.processSingleMessage(id);
                } catch (Exception e) {
                    worker.handleFailureAndRetry(id, e.getMessage());
                }
            }

            hasMore = ids.size() == BATCH_SIZE;

        } while (hasMore);
    }
}