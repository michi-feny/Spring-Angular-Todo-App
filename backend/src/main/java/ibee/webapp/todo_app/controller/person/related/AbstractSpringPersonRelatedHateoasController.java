
package ibee.webapp.todo_app.controller.person.related;

// 1. Add the static import
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import ibee.webapp.todo_app.controller.baseController.hateosCrud.AbstractSpringHateoasCrudController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractMappedPersonRelatedDtoService;
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
        ENTITY extends PersonRelatedEntity<ID>,
        ID,
        IDDTO>
        extends AbstractSpringHateoasCrudController<DTO, IDDTO> 
        //implements CrudDtoService<DTO, IDDTO>
{

protected final AbstractMappedPersonRelatedDtoService<DTO, ENTITY, ID, IDDTO> personRelatedService;

    protected AbstractSpringPersonRelatedHateoasController(
            AbstractMappedPersonRelatedDtoService<DTO, ENTITY, ID, IDDTO> service,
            TranslationService translationService,
            AbstractHateoasAssembler<DTO, IDDTO> assembler,
            String entityKey) {
        super(service, translationService, assembler, entityKey);
        this.personRelatedService = service;
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<DTO>>>> getByPersonId(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("personId") Long personId) {
        
        List<DTO> list = personRelatedService.findByPersonId(personId);
        CollectionModel<EntityModel<DTO>> collectionModel = assembler.toCollectionModel(list);
        //String message = translationService.translate("crud.loadedAllForPerson", getEntityName());

        String message = list.isEmpty() 
            ? translationService.translate("crud.emptyListForPerson", getEntityName())
            : translationService.translate("crud.loadedAllForPerson", getEntityName());

        // 2. Use the builder!
        return buildResponse(collectionModel, message);
    }

    @GetMapping("/person/{personId}/ids")
    public ResponseEntity<ApiSuccessResponse<List<IDDTO>>> getIdsByPersonId(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("personId") Long personId) {
        
        List<IDDTO> list = personRelatedService.findIdsByPersonId(personId);
        //String message = translationService.translate("crud.loadedIdsForPerson", getEntityName());

        String message = list.isEmpty() 
            ? translationService.translate("crud.emptyListForPerson", getEntityName())
            : translationService.translate("crud.loadedIdsForPerson", getEntityName());

        // 3. Use the builder!
        return buildResponse(list, message);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ApiSuccessResponse<EntityModel<DTO>>> getWithDetailsById(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @PathVariable("id") IDDTO id) {
        
        DTO dto = personRelatedService.findWithDetailsById(id);
                //no null check needed, 
                // cause the service for the entity would throw a ressourceNotFOundException
        
        //or hte Assembler would throw a not null dto input Exception
        EntityModel<DTO> entityModel = assembler.toModel(dto);
        String message = translationService.translate("crud.loadedWithDetails", getEntityName());

        // 4. Use the builder!
        return buildResponse(entityModel, message);
    }
}
