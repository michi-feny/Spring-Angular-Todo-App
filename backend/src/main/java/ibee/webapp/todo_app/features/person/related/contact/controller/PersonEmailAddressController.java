package ibee.webapp.todo_app.features.person.related.contact.controller;

import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import java.util.List;

import ibee.webapp.todo_app.features.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.features.person.related.contact.controller.assembler.PersonEmailAddressModelAssembler;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonEmailAddressDto;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonEmailAddressDtoService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-email-addresses")
public class PersonEmailAddressController extends AbstractSpringPersonRelatedHateoasController<PersonEmailAddressDto, PersonEmailAddressId, PersonEmailAddressDtoId> {

    private final PersonEmailAddressDtoService personEmailAddressService;
    private final PersonEmailAddressModelAssembler concreteAssembler;

    public PersonEmailAddressController(
            PersonEmailAddressDtoService personEmailAddressService,
            TranslationService translationService,
            PersonEmailAddressModelAssembler assembler) {
        
        super(personEmailAddressService, translationService, assembler, "entity.personEmailAddress");
        this.personEmailAddressService = personEmailAddressService;
        this.concreteAssembler = assembler;
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonEmailAddressDto>>>> create(
            @RequestBody @Validated(OnCreate.class) PersonEmailAddressDto dto) {
        
        ServiceResult<PersonEmailAddressDto> result = personEmailAddressService.create(dto);
        
        if (result.isRejected()) {
            return buildConflictResponse(dto, result);
        }

        return buildSuccessResponse(result, "crud.created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonEmailAddressDto>>>> update(
            @PathVariable("id") PersonEmailAddressDtoId id,
            @RequestBody @Validated(OnUpdate.class) PersonEmailAddressDto dto) {
        
        ServiceResult<PersonEmailAddressDto> result = personEmailAddressService.update(dto, id);
        
        if (result.isRejected()) {
            return buildConflictResponse(dto, result);
        }
        
        return buildSuccessResponse(result, "crud.updated", HttpStatus.OK);
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonEmailAddressDto>>>> buildConflictResponse(
            PersonEmailAddressDto inputDto, ServiceResult<PersonEmailAddressDto> result) {
        
        
        // Find the ID of the email address that is CURRENTLY set as the main email
        PersonEmailAddressDto rejectedDtoWithExisting = result.value();
        
        // Enrich the rejected DTO with the existing ID so the frontend can immediately send an overwrite request
    
        Link repairLink = concreteAssembler.getRepairLinkForUpdate(rejectedDtoWithExisting.id());
        
        var rejectedResultModel = EntityModel.of(rejectedDtoWithExisting);
        var rejectedServiceResult = ServiceResult.rejected(rejectedResultModel, result.violations());
        String rejectMessage = translationService.translate("crud.validationFailed", getEntityName());
        
        return buildResponse(
                rejectedServiceResult, 
                rejectMessage,
                List.of(repairLink), 
                HttpStatus.CONFLICT
        );
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonEmailAddressDto>>>> buildSuccessResponse(
            ServiceResult<PersonEmailAddressDto> result, String translationKey, HttpStatus status) {
        
        EntityModel<PersonEmailAddressDto> entityModel = concreteAssembler.toModel(result.value());
        ServiceResult<EntityModel<PersonEmailAddressDto>> successServiceResult = ServiceResult.success(entityModel);
        String successMessage = translationService.translate(translationKey, getEntityName());
        
        return buildResponse(successServiceResult, successMessage, status);
    }
}
