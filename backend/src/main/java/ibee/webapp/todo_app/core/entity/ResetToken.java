package ibee.webapp.todo_app.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@Table(name = "reset_tokens", indexes = {
        @Index(name = "idx_reset_token_string", columnList = "token"),
        @Index(name = "idx_reset_user_id", columnList = "user_id")
})
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Instant expiresAt;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    public ResetToken(String token, User user) {
        this.token = token;
        this.expiresAt = Instant.now().plus(24, ChronoUnit.HOURS);
        this.user = user;
    }
}
