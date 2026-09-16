

package ibee.webapp.todo_app.controller.baseController.hateosCrud;

// 1. ADD THIS STATIC IMPORT
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.exception.ResourceNotFoundException;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.CrudDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//TODO: take the Exceptions and give them into the Service!!!

/**
 * Abstract generic REST controller providing HATEOAS-compliant endpoints.
 * Handles HTTP request routing, security, validation, and user-facing message translation.
 *
 * @param <T>  The internal Entity type
 * @param <ID> The type of the entity's primary key
 * @param <DTO> The Data Transfer Object type
 */
public abstract class AbstractSpringHateoasCrudController<DTO, ID> {

    protected final CrudDtoService<DTO, ID> service;
    protected final TranslationService translationService;
    protected final AbstractHateoasAssembler<DTO, ID> assembler;
    protected final String entityKey;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

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
       // Correct Spring HATEOAS: Use the assembler to wrap the list and generate collection links
        CollectionModel<EntityModel<DTO>> collectionModel = assembler.toCollectionModel(list);
        String message = translationService.translate("crud.loadedAll", getEntityName());
        // Note: ApiSuccessResponse now takes CollectionModel instead of a raw List
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> getById(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable("id") ID id) {
        
        DTO dto = service.findById(id);
        //service.findByIdWithoutException(id);
                // .orElseThrow(() -> 
                //     new ResourceNotFoundException(
                //         translationService.translate(
                //                 "crud.notFound", 
                //                 getEntityName(), 
                //                 id.toString()
                //         ),
                //         "crud.notFound"
                //     )
                // );

        EntityModel<DTO> entityModel = assembler.toModel(dto);
        String message = translationService.translate("crud.loaded", getEntityName());

        return buildResponse(entityModel, message);
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> create(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @Validated(OnCreate.class) @RequestBody DTO dto) {

        DTO created = service.create(dto);
        EntityModel<DTO> entityModel = assembler.toModel(created);
        String message = translationService.translate("crud.created", getEntityName());

        return buildResponse(entityModel, message, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> update(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable("id") ID id,
            @NotNull @Validated(OnUpdate.class) @RequestBody DTO dto) {

                //TODO: CHECK DEEP DPWN TO ENTITY SERVICER
        // if (!service.existsById(id)) {
        //     throw new RuntimeException(translationService.translate("crud.notFound", getEntityName(), id.toString()));
        // }

        DTO updated = service.update(dto, id);
        EntityModel<DTO> entityModel = assembler.toModel(updated);
        String message = translationService.translate("crud.updated", getEntityName());

        return buildResponse(entityModel, message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> delete(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @NotNull @PathVariable("id") ID id) {

                //TODO: CHECK DEEP DPWN TO ENTITY SERVICER
        // if (!service.existsById(id)) {
        //     throw new RuntimeException(translationService.translate("crud.notFound", getEntityName(), id.toString()));
        // }

        service.deleteById(id);
        String message = translationService.translate("crud.deleted", getEntityName());

        return buildEmptyResponse(message, HttpStatus.OK);
    }
}
