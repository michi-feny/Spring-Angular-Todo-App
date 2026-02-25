package ibee.webapp.todo_app.core.service;

import ibee.webapp.todo_app.core.dto.response.TodoListResponse;
import ibee.webapp.todo_app.core.dto.requests.CreateTodo;
import ibee.webapp.todo_app.core.dto.requests.UpdateTodo;
import ibee.webapp.todo_app.core.entity.Todo;
import ibee.webapp.todo_app.core.entity.User;
import ibee.webapp.todo_app.core.exception.TodoNotFoundException;
import ibee.webapp.todo_app.core.repository.TodoRepository;
import ibee.webapp.todo_app.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository repo;
    private final UserRepository userRepo;

    public TodoListResponse findAll(Pageable pageable, Long userId) {
        Page<@NotNull Todo> todoPage = repo.findByUserId(userId, pageable);

        return new TodoListResponse(
            todoPage.getContent(),
            todoPage.getTotalElements(),
            todoPage.getNumber(),
            todoPage.getSize(),
            todoPage.getSort().toString()
        );
    }

    public Todo findById(Long id, Long userId) {
        return repo.findByIdAndUserId(id, userId).orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Transactional
    public Todo create(CreateTodo dto, Long userId) {
        User userReference = userRepo.getReferenceById(userId);

        Todo todo = new Todo(
                dto.title(),
                dto.description(),
                userReference
        );

        return repo.save(todo);
    }

    @Transactional
    public Todo update(UpdateTodo dto, Long userId) {
        Todo todo = findById(dto.id(), userId);

        todoToUpdate.setTitle(dto.title());
        todoToUpdate.setDescription(dto.description());
        todoToUpdate.setIsDone(dto.isDone());

        return repo.save(todoToUpdate);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Todo todo = findById(id, userId);

        repo.delete(todo);
    }
}
