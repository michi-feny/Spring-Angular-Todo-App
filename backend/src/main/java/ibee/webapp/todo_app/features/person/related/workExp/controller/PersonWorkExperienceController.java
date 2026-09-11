package ibee.webapp.todo_app.features.person.related.workExp.controller;

import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.buildResponse;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibee.webapp.todo_app.controller.baseController.hateosCrud.AbstractSpringHateoasCrudController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedService;
import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import ibee.webapp.todo_app.features.person.related.workExp.dto.MergeWorkExperiencesRequestDto;
import ibee.webapp.todo_app.features.person.related.workExp.dto.PersonWorkExperienceDto;
import ibee.webapp.todo_app.features.person.related.workExp.service.PersonWorkExperienceDtoServiceImpl;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.AuthenticatedUser;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person-work-experiences")
public class PersonWorkExperienceController 
        extends AbstractSpringHateoasCrudController
            <PersonWorkExperienceDto, PersonWorkExperienceDtoId> {

    // Retain a strongly typed reference to access custom methods not present in CrudDtoService
    private final PersonWorkExperienceDtoServiceImpl customService;

    public PersonWorkExperienceController(
            PersonWorkExperienceDtoServiceImpl customService,
            TranslationService translationService,
            PersonWorkExperienceHateoasAssembler assembler) {
        
        // Fulfill the base controller's generic dependencies
        super(customService, translationService, assembler, "personWorkExperience");
        this.customService = customService;
        
    }

    /**
     * Custom endpoint for bulk merging.
     * Inherits the unified HATEOAS response structure from the base architecture.
     */
    @PostMapping("/merge")
    public ResponseEntity<ApiSuccessResponse<CollectionModel<EntityModel<PersonWorkExperienceDto>>>> mergeWorkExperiences(
            @AuthenticationPrincipal AuthenticatedUser userDetails,
            @Validated @RequestBody MergeWorkExperiencesRequestDto request) {

        // 1. Execute the merge and fetch the synchronized gap-free sequence
        List<PersonWorkExperienceDto> refreshedList = customService.mergeWorkExperiences(request);

        // 2. Wrap the result in HATEOAS models utilizing the injected assembler
        CollectionModel<EntityModel<PersonWorkExperienceDto>> collectionModel = 
                assembler.toCollectionModel(refreshedList);

        // 3. Resolve the localized success message
        String message = translationService.translate("crud.merged", getEntityName());

        // 4. Return via standard builder
        return buildResponse(collectionModel, message, HttpStatus.OK);
    }
}
