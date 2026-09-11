package ibee.webapp.todo_app.features.person.related.contact.controller;

import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import java.util.List;

import ibee.webapp.todo_app.features.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.features.person.related.contact.controller.assembler.PersonCountryModelAssembler;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonCountryDto;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonCountryDtoService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person-countries")
public class PersonCountryController extends AbstractSpringPersonRelatedHateoasController<PersonCountryDto, PersonCountryId, PersonCountryDtoId> {

    private final PersonCountryDtoService personCountryService;
    private final PersonCountryModelAssembler concreteAssembler;

    public PersonCountryController(
            PersonCountryDtoService personCountryService,
            TranslationService translationService,
            PersonCountryModelAssembler assembler) {
        
        super(personCountryService, translationService, assembler, "entity.personCountry");
        this.personCountryService = personCountryService;
        this.concreteAssembler = assembler;
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonCountryDto>>>> create(
            @RequestBody @Validated(OnCreate.class) PersonCountryDto dto) {
        
        ServiceResult<PersonCountryDto> result = personCountryService.create(dto);
        if (result.isRejected()) return buildConflictResponse(dto, result);
        return buildSuccessResponse(result, "crud.created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonCountryDto>>>> update(
            @PathVariable("id") PersonCountryDtoId id,
            @RequestBody @Validated(OnUpdate.class) PersonCountryDto dto) {
        
        ServiceResult<PersonCountryDto> result = personCountryService.update(dto, id);
        if (result.isRejected()) return buildConflictResponse(dto, result);
        return buildSuccessResponse(result, "crud.updated", HttpStatus.OK);
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonCountryDto>>>> buildConflictResponse(
            PersonCountryDto inputDto, ServiceResult<PersonCountryDto> result) {
        
        PersonCountryDto rejectedDto = result.value();
        PersonCountryDtoId rejectedId = rejectedDto.id();
        
        Link repairLink = concreteAssembler.getRepairLinkForUpdate(rejectedId);
        
        var rejectedResultModel = EntityModel.of(rejectedDto);
        var rejectedServiceResult = ServiceResult.rejected(rejectedResultModel, result.violations());
        String rejectMessage = translationService.translate("crud.validationFailed", getEntityName());
        
        return buildResponse(rejectedServiceResult, rejectMessage, List.of(repairLink), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonCountryDto>>>> buildSuccessResponse(
            ServiceResult<PersonCountryDto> result, String translationKey, HttpStatus status) {
        
        EntityModel<PersonCountryDto> entityModel = concreteAssembler.toModel(result.value());
        ServiceResult<EntityModel<PersonCountryDto>> successServiceResult = ServiceResult.success(entityModel);
        String successMessage = translationService.translate(translationKey, getEntityName());
        
        return buildResponse(successServiceResult, successMessage, status);
    }
}
