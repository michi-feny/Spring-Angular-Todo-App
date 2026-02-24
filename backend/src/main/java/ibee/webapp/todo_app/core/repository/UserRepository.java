package ibee.webapp.todo_app.core.repository;

import ibee.webapp.todo_app.core.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NotNull User, @NotNull Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}