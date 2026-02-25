package ibee.webapp.todo_app.core.repository;

import ibee.webapp.todo_app.core.entity.OutBoxMessage;
import jakarta.persistence.LockModeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OutboxMessageRepository extends JpaRepository<@NotNull OutBoxMessage, @NotNull Long> {
    @Query("SELECT m.id FROM OutBoxMessage m WHERE m.isProcessed = false " +
            "AND m.failedPermanently = false AND m.nextAttempt <= :now " +
            "ORDER BY m.nextAttempt ASC")
    List<Long> findPendingIds(LocalDateTime now, Pageable pageable);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM OutBoxMessage m WHERE m.id = :id")
    Optional<OutBoxMessage> findByIdWithLock(Long id);
}