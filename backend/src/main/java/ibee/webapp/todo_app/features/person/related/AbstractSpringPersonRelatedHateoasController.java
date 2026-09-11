package ibee.webapp.todo_app.features.person.related;

import ibee.webapp.todo_app.features.person.related.AbstractSpringPersonRelatedHateoasController;
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.service.baseService.transport.PersonRelatedQueryDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

// Notice: No ENTITY generic needed here anymore because the web layer only cares about DTOs and IDs!
public abstract class AbstractSpringPersonRelatedHateoasController<DTO, ID, IDDTO> {

    // Binds strictly to your Query interface
    protected final PersonRelatedQueryDtoService<DTO, IDDTO> queryService;
    protected final TranslationService translationService;
    protected final AbstractHateoasAssembler<DTO, IDDTO> assembler;
    protected final String entityKey;

    protected AbstractSpringPersonRelatedHateoasController(
            PersonRelatedQueryDtoService<DTO, IDDTO> queryService,
            TranslationService translationService,
            AbstractHateoasAssembler<DTO, IDDTO> assembler,
            String entityKey) {
        
        this.queryService = queryService;
        this.translationService = translationService;
        this.assembler = assembler;
        this.entityKey = entityKey;
    }

    protected String getEntityName() {
        return translationService.translate(entityKey, entityKey);
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>> getByPersonId(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("personId") Long personId) {
        
        List<DTO> list = queryService.findByPersonId(personId);
        return buildResponse(assembler.toCollectionModel(list), 
            translationService.translate("crud.loadedAllForPerson", getEntityName()));
    }

    @GetMapping("/person/{personId}/ids")
    public ResponseEntity<ApiSuccessResponse<List<IDDTO>>> getIdsByPersonId(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("personId") Long personId) {
        
        List<IDDTO> list = queryService.findIdsByPersonId(personId);
        return buildResponse(list, 
            translationService.translate("crud.loadedIdsForPerson", getEntityName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> getWithDetailsById(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("id") IDDTO id) {
        
        // FIXED: The service guarantees a DTO 
        // or throws the exception internally. No Optional!
        //so should never be null!
        DTO dto = queryService.findWithDetailsById(id);

        return buildResponse(assembler.toModel(dto), 
            translationService.translate("crud.loadedWithDetails", getEntityName()));
    }

    // You can also include standard DELETE here since it extends QueryAndDeleteDtoService
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> delete(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("id") IDDTO id) {

        queryService.deleteById(id);
        return buildEmptyResponse(
            translationService.translate("crud.deleted", getEntityName()), 
            HttpStatus.OK
        );
    }
}
