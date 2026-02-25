package ibee.webapp.todo_app.core.repository;

import ibee.webapp.todo_app.core.entity.Todo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<@NotNull Todo, @NotNull Long> {
    Page<@NotNull Todo> findByUserId(Long userId, Pageable pageable);
    Optional<Todo> findByIdAndUserId(Long id, Long userId);
}
