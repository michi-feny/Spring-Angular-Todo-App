package ibee.webapp.todo_app.controller.baseController;
//https://mayankposts.medium.com/simplify-crud-with-a-generic-controller-to-reduce-boilerplate-code-in-spring-boot-cac97b21cc36


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import ibee.webapp.todo_app.core.service.baseService.oldApproach.CrudServiceForSimpleLongKey;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public abstract class CrudControllerForSimpleLongKey<DTO,ENTITY, ID> {

    private final CrudServiceForSimpleLongKey<DTO, ENTITY, ID> service;
    

    protected CrudControllerForSimpleLongKey(
        CrudServiceForSimpleLongKey<DTO, ENTITY, ID> service
        ) {
        this.service = service;
    }

    @GetMapping("/all")
    /**
     * 
     * @param userDetails
     * @return
     */
    public ResponseEntity<List<ENTITY>> getAll(
            @NotNull @AuthenticationPrincipal AuthenticatedUser userDetails){
        return ResponseEntity.ok(
                
                        service.getAll()
               
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ENTITY> getById(
        @AuthenticationPrincipal AuthenticatedUser userDetails,
        @NotNull @PathVariable ID id) {
            return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DTO> create(
        @NotNull @AuthenticationPrincipal AuthenticatedUser userDetails,
        @NotNull @Valid @RequestBody DTO dto ){
            DTO created =
                service.createFromDto(dto);
                

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        created
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> updateFromDto(
        @AuthenticationPrincipal AuthenticatedUser userDetails,
        @NotNull @PathVariable ID id,
        @NotNull  @Valid @RequestBody DTO dto) {
        if (!service.existsById(id)) {

            return ResponseEntity.notFound().build();
        }

        DTO updated =
                service.updateFromDto(
                        id,
                        dto
                );

        return ResponseEntity.ok(
                updated
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DTO> partialUpdate(
        
        @NotNull @PathVariable ID id, 
        @NotNull @Valid @RequestBody DTO updates) {
        return service.getDtoById(id)
                .map(update -> {
                    // Apply partial updates to entity here
                    var updatedDto = service.updateFromDto(id, updates);
                    return new ResponseEntity<>(updatedDto, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable ID id) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
