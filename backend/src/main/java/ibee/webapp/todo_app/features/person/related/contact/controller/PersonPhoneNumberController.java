package ibee.webapp.todo_app.features.person.related.contact.controller;
import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import java.util.List;

import ibee.webapp.todo_app.controller.support.Link;

import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.features.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.features.person.related.contact.controller.assembler.PersonPhoneNumberModelAssembler;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonPhoneNumberDto;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonPhoneNumberDtoService;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.core.service.baseService.transport.PersonRelatedQueryDtoService;
import ibee.webapp.todo_app.core.service.baseService.transport.businessRuleMainFlag.BusinessWriteDtoService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person-phone-numbers")
public class PersonPhoneNumberController 
    extends AbstractSpringPersonRelatedHateoasController
        <PersonPhoneNumberDto, 
        PersonPhoneNumberId, 
        PersonPhoneNumberDtoId> 
{

    private final PersonPhoneNumberDtoService personPhoneNumberService;
    private final PersonPhoneNumberModelAssembler concreteAssembler;

    public PersonPhoneNumberController(
            PersonPhoneNumberDtoService personPhoneNumberService,
            TranslationService translationService,
            PersonPhoneNumberModelAssembler assembler) {
        
        super(personPhoneNumberService, translationService, assembler, "entity.personPhoneNumber");
        this.personPhoneNumberService = personPhoneNumberService;
        this.concreteAssembler = assembler;
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonPhoneNumberDto>>>> create(
            @RequestBody @Validated(OnCreate.class) PersonPhoneNumberDto dto) {
        
        ServiceResult<PersonPhoneNumberDto> result = personPhoneNumberService.create(dto);
        
        if (result.isRejected()) {
            return buildConflictResponse(dto, result);
        }

        return buildSuccessResponse(result, "crud.created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonPhoneNumberDto>>>> update(
            @PathVariable("id") PersonPhoneNumberDtoId id,
            @RequestBody @Validated(OnUpdate.class) PersonPhoneNumberDto dto) {
        
        ServiceResult<PersonPhoneNumberDto> result = personPhoneNumberService.update(dto, id);
        
        if (result.isRejected()) {
            return buildConflictResponse(dto, result);
        }
        
        return buildSuccessResponse(result, "crud.updated", HttpStatus.OK);
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonPhoneNumberDto>>>> buildConflictResponse(
            PersonPhoneNumberDto inputDto, ServiceResult<PersonPhoneNumberDto> result) {
        
        
        PersonPhoneNumberDto rejectedDtoWithExistingId = result.value();
        Link repairLink = concreteAssembler.getRepairLinkForUpdate(rejectedDtoWithExistingId.id());
        
        var rejectedResultModel = EntityModel.of(rejectedDtoWithExistingId);
        var rejectedServiceResult = ServiceResult.rejected(rejectedResultModel, result.violations());
        String rejectMessage = translationService.translate("crud.validationFailed", getEntityName());
        
        return buildResponse(
                rejectedServiceResult, 
                rejectMessage,
                List.of(repairLink), 
                HttpStatus.CONFLICT
        );
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonPhoneNumberDto>>>> buildSuccessResponse(
            ServiceResult<PersonPhoneNumberDto> result, String translationKey, HttpStatus status) {
        
        EntityModel<PersonPhoneNumberDto> entityModel = concreteAssembler.toModel(result.value());
        ServiceResult<EntityModel<PersonPhoneNumberDto>> successServiceResult = ServiceResult.success(entityModel);
        String successMessage = translationService.translate(translationKey, getEntityName());
        
        return buildResponse(successServiceResult, successMessage, status);
    }
}
