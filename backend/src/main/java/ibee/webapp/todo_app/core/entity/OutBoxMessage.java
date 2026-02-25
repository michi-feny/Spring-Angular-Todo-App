package ibee.webapp.todo_app.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_messages", indexes = {
        @Index(name = "idx_outbox_scheduler", columnList = "isProcessed, failedPermanently, nextAttempt")
})
@Data
public class OutBoxMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime occurredOn = LocalDateTime.now();

    private boolean isProcessed = false;

    private boolean failedPermanently = false;


    private int retryCount = 0;

    @Column(nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime nextAttempt = LocalDateTime.now();

    private LocalDateTime processedOn;
    @Lob
    private String errorMessage;
}

