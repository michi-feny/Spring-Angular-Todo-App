package ibee.webapp.todo_app.controller.baseController;
//https://mayankposts.medium.com/simplify-crud-with-a-generic-controller-to-reduce-boilerplate-code-in-spring-boot-cac97b21cc36


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import ibee.webapp.todo_app.core.service.baseService.oldApproach.CrudServiceForSimpleLongKey;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public abstract class CrudControllerForSimpleLongKey<DTO,ENTITY> {

    private final CrudServiceForSimpleLongKey<ENTITY> service;
    protected final BaseMapper<DTO, ENTITY> mapper;

    protected CrudControllerForSimpleLongKey(
        CrudServiceForSimpleLongKey<ENTITY> service,
        BaseMapper<DTO, ENTITY> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    /**
     * 
     * @param userDetails
     * @return
     */
    public ResponseEntity<List<DTO>> getAll(
            @NotNull @AuthenticationPrincipal AuthenticatedUser userDetails){
        return ResponseEntity.ok(
                mapper.toDtoList(
                        service.getAll()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(
        @AuthenticationPrincipal AuthenticatedUser userDetails,
        @NotNull @PathVariable Long id) {
            return service.getById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DTO> create(
        @NotNull @AuthenticationPrincipal AuthenticatedUser userDetails,
        @NotNull @Valid @RequestBody DTO dto ){
            ENTITY created =
                service.create(
                        mapper.toEntity(dto)
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        mapper.toDto(created)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(
        @AuthenticationPrincipal AuthenticatedUser userDetails,
        @NotNull @PathVariable Long id,
        @NotNull  @Valid @RequestBody DTO dto) {
        if (!service.existsById(id)) {

            return ResponseEntity.notFound().build();
        }

        ENTITY updated =
                service.update(
                        id,
                        mapper.toEntity(dto)
                );

        return ResponseEntity.ok(
                mapper.toDto(updated)
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DTO> partialUpdate(
        
        @NotNull @PathVariable Long id, 
        @NotNull @Valid @RequestBody DTO updates) {
        return service.getById(id)
                .map(entity -> {
                    // Apply partial updates to entity here
                    service.update(id, entity);
                    return new ResponseEntity<>(entity, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable Long id) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
