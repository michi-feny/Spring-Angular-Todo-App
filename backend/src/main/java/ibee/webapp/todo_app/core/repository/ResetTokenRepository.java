package ibee.webapp.todo_app.core.repository;

import ibee.webapp.todo_app.core.entity.ResetToken;
import ibee.webapp.todo_app.core.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetTokenRepository extends JpaRepository<@NotNull ResetToken, @NotNull  Long> {
    Optional<ResetToken> findByToken(String token);
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM ResetToken rt WHERE rt.user.id = :#{#user.id}")
    void deleteByUser(@Param("user") User user);
}
