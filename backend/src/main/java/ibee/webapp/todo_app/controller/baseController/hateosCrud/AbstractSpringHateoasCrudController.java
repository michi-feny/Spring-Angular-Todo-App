

package ibee.webapp.todo_app.controller.baseController.hateosCrud;

// 1. ADD THIS STATIC IMPORT
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.service.baseService.newApproach.CrudDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO: take the Exceptions and give them into the Service!!!

public abstract class AbstractSpringHateoasCrudController<DTO, ID> {

    protected final CrudDtoService<DTO, ID> service;
    protected final TranslationService translationService;
    protected final AbstractHateoasAssembler<DTO, ID> assembler;
    protected final String entityKey;

    protected AbstractSpringHateoasCrudController(
            CrudDtoService<DTO, ID> service,
            TranslationService translationService,
            AbstractHateoasAssembler<DTO, ID> assembler,
            String entityKey) {
        this.service = service;
        this.translationService = translationService;
        this.assembler = assembler;
        this.entityKey = entityKey;
    }

    protected String getEntityName() {
        return translationService.translate(entityKey, entityKey);
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>> getAll(
            @AuthenticationPrincipal AuthenticatedUser userDetails) {
        
        List<DTO> list = service.findAll();
        CollectionModel<EntityModel<DTO>> collectionModel = assembler.toCollectionModel(list);
        String message = translationService.translate("crud.loadedAll", getEntityName());

        // Note: ApiSuccessResponse now takes CollectionModel instead of a raw List
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> getById(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable ID id) {
        
        DTO dto = service.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    translationService.translate("crud.notFound", getEntityName(), id.toString())
                ));

        EntityModel<DTO> entityModel = assembler.toModel(dto);
        String message = translationService.translate("crud.loaded", getEntityName());

        return buildResponse(entityModel, message);
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> create(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @Valid @RequestBody DTO dto) {

        DTO created = service.save(dto);
        EntityModel<DTO> entityModel = assembler.toModel(created);
        String message = translationService.translate("crud.created", getEntityName());

        return buildResponse(entityModel, message, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> update(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable ID id,
            @NotNull @Valid @RequestBody DTO dto) {

        if (!service.existsById(id)) {
            throw new RuntimeException(translationService.translate("crud.notFound", getEntityName(), id.toString()));
        }

        DTO updated = service.save(dto);
        EntityModel<DTO> entityModel = assembler.toModel(updated);
        String message = translationService.translate("crud.updated", getEntityName());

        return buildResponse(entityModel, message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> delete(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable ID id) {

        if (!service.existsById(id)) {
            throw new RuntimeException(translationService.translate("crud.notFound", getEntityName(), id.toString()));
        }

        service.deleteById(id);
        String message = translationService.translate("crud.deleted", getEntityName());

        return buildEmptyResponse(message, HttpStatus.OK);
    }
}
