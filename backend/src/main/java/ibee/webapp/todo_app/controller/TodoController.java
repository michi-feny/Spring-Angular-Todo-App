package ibee.webapp.todo_app.controller;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.core.dto.response.TodoListResponse;
import ibee.webapp.todo_app.core.dto.requests.CreateTodo;
import ibee.webapp.todo_app.core.dto.requests.UpdateTodo;
import ibee.webapp.todo_app.core.entity.Todo;
import ibee.webapp.todo_app.core.service.TodoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private static final String PATH = "/todos";

    private final TodoService todoService;
    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<@NotNull ApiSuccessResponse<TodoListResponse>> getAll(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            Pageable pageable) {

        TodoListResponse todoListResponse = todoService.findAll(pageable, userDetails.getId());

        return ResponseEntity.ok(
            new ApiSuccessResponse<>(todoListResponse, buildPaginationLinks(todoListResponse))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NotNull ApiSuccessResponse<Todo>> get(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUser userDetails) {

        Todo todo = todoService.findById(id, userDetails.getId());

        List<Link> links = List.of(
                createLink("self", PATH + "/" + id, "GET"),
                createLink("create", PATH, "POST"),
                createLink("update", PATH, "PUT"),
                createLink("delete", PATH + "/" + id, "DELETE"),
                createLink("list", PATH, "GET")
        );

        return ResponseEntity.ok(
            new ApiSuccessResponse<>(todo, links)
        );
    }

    @PostMapping
    public ResponseEntity<@NotNull ApiSuccessResponse<Todo>> create(
            @Valid @RequestBody CreateTodo request,
            @AuthenticationPrincipal AuthenticatedUser userDetails) {

        Todo todo =  todoService.create(request, userDetails.getId());

        List<Link> links = List.of(
                createLink("self", PATH, "POST"),
                createLink("update", PATH, "PUT"),
                createLink("delete", PATH + "/" + todo.getId(), "DELETE"),
                createLink("get", PATH + "/" + todo.getId(), "GET"),
                createLink("list", PATH, "GET")
        );

        return new ResponseEntity<>(new ApiSuccessResponse<>(
            todo,
            translationService.translate("todo.created"),
            links
        ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<@NotNull ApiSuccessResponse<Todo>> update(
            @Valid @RequestBody UpdateTodo request,
            @AuthenticationPrincipal AuthenticatedUser userDetails) {

        Todo updatedToto = todoService.update(request, userDetails.getId());

        List<Link> links = List.of(
                createLink("self", PATH, "PUT"),
                createLink("create", PATH, "POST"),
                createLink("delete", PATH + "/" + request.id(), "DELETE"),
                createLink("get", PATH + "/" + request.id(), "GET"),
                createLink("list", PATH, "GET")
        );

        return ResponseEntity.ok(new ApiSuccessResponse<>(
                updatedToto,
                translationService.translate("todo.updated"),
                links
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<@NotNull ApiSuccessResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUser userDetails) {

        todoService.delete(id, userDetails.getId());

        List<Link> links = List.of(
                createLink("list", PATH, "GET"),
                createLink("create", PATH, "POST")
        );

        return ResponseEntity.ok(new ApiSuccessResponse<Void>(
                translationService.translate("todo.deleted"),
                links
        ));
    }

    private Link createLink(String rel, String href, String method) {
        return Link.builder().rel(rel).href(href).method(method).build();
    }

    private List<Link> buildPaginationLinks(TodoListResponse page) {
        List<Link> links = new ArrayList<>();
        int currentPage = page.currentPage();
        int pageSize = page.size();
        int totalPages = (int) Math.ceil((double) page.totalTodos() / pageSize);

        links.add(createLink("self", String.format("%s?page=%d&size=%d", PATH, currentPage, pageSize), "GET"));

        if (currentPage > 0) {
            links.add(createLink("first", String.format("%s?page=0&size=%d", PATH, pageSize), "GET"));
            links.add(createLink("prev", String.format("%s?page=%d&size=%d", PATH, currentPage - 1, pageSize), "GET"));
        }

        if (currentPage < totalPages - 1 && totalPages > 0) {
            links.add(createLink("next", String.format("%s?page=%d&size=%d", PATH, currentPage + 1, pageSize), "GET"));
            links.add(createLink("last", String.format("%s?page=%d&size=%d", PATH, totalPages - 1, pageSize), "GET"));
        }

        return links;
    }
}
