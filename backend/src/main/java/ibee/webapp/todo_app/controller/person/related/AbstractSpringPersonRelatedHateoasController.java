
package ibee.webapp.todo_app.controller.person.related;

// 1. Add the static import
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.baseController.hateosCrud.AbstractSpringHateoasCrudController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class AbstractSpringPersonRelatedHateoasController<
        DTO,
        ENTITY extends PersonRelatedEntity,
        ID,
        IDDTO>
        extends AbstractSpringHateoasCrudController<DTO, ID> {

    protected final PersonRelatedDtoService<DTO, ENTITY, ID, IDDTO> personRelatedService;

    protected AbstractSpringPersonRelatedHateoasController(
            PersonRelatedDtoService<DTO, ENTITY, ID, IDDTO> service,
            TranslationService translationService,
            AbstractHateoasAssembler<DTO, ID> assembler,
            String entityKey) {
        
        super(service, translationService, assembler, entityKey);
        this.personRelatedService = service;
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>> getByPersonId(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable Long personId) {
        
        List<DTO> list = personRelatedService.findByPersonId(personId);
        CollectionModel<EntityModel<DTO>> collectionModel = assembler.toCollectionModel(list);
        String message = translationService.translate("crud.loadedAllForPerson", getEntityName());

        // 2. Use the builder!
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/person/{personId}/ids")
    public ResponseEntity<ApiSuccessResponse<List<IDDTO>>> getIdsByPersonId(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable Long personId) {
        
        List<IDDTO> list = personRelatedService.findIdsByPersonId(personId);
        String message = translationService.translate("crud.loadedIdsForPerson", getEntityName());

        // 3. Use the builder!
        return buildResponse(list, message);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> getWithDetailsById(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable ID id) {
        
        DTO dto = personRelatedService.findWithDetailsById(id)
                .orElseThrow(() -> new RuntimeException(
                    translationService.translate("crud.notFound", getEntityName(), id.toString())
                ));

        EntityModel<DTO> entityModel = assembler.toModel(dto);
        String message = translationService.translate("crud.loadedWithDetails", getEntityName());

        // 4. Use the builder!
        return buildResponse(entityModel, message);
    }
}
