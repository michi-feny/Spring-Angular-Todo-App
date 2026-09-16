package ibee.webapp.todo_app.features.person.related.contact.controller;

import static ibee.webapp.todo_app.controller.support.hateoas.builder.ApiResponseBuilder.*;

import java.util.List;

import ibee.webapp.todo_app.features.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.controller.support.ApiSuccessResponse;
import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.result.ServiceResult;
import ibee.webapp.todo_app.features.person.related.contact.controller.assembler.PersonAddressModelAssembler;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.contact.service.PersonAddressDtoService;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
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
/*
PersonAddressController
Base Route: /api/v1/person-addresses

1. Standard CRUD Endpoints (Inherited from AbstractSpringHateoasCrudController)
Get All: GET /api/v1/person-addresses

Retrieves a HATEOAS collection model of all person addresses in the system inside an ApiSuccessResponse.

Get By ID: GET /api/v1/person-addresses/{id}

Retrieves a single address entity model by its primary ID, complete with automatic HATEOAS links (self, update, delete, list).

Create: POST /api/v1/person-addresses

Accepts a validated PersonAddressDto request body, saves it, and returns the newly created resource model with a 201 CREATED status.

Update: PUT /api/v1/person-addresses/{id}

Updates an existing address record matching the path ID with the provided request body DTO.

Delete: DELETE /api/v1/person-addresses/{id}

Deletes the address record matching the given ID and returns a successful empty response.

2. Person-Specific Endpoints (Inherited from AbstractSpringPersonRelatedHateoasController)
Get All By Person ID: GET /api/v1/person-addresses/person/{personId}

Retrieves a collection model of all address records tied directly to a specific person's ID.

Get Reference IDs By Person ID: GET /api/v1/person-addresses/person/{personId}/ids

Retrieves a lightweight list of reference ID DTOs (PersonAddressDtoId) for a given person.

Get With Details By ID: GET /api/v1/person-addresses/{id}/details

Fetches an extended, detailed view of a specific address entity model.
*/
@RestController
@RequestMapping("/api/v1/person-addresses")
@Validated
public class PersonAddressController 
    extends AbstractSpringPersonRelatedHateoasController
        <PersonAddressDto, PersonAddressId, PersonAddressDtoId> {

    private final PersonAddressDtoService personAddressService;
    private final PersonAddressModelAssembler concreteAssembler;

    public PersonAddressController(
            PersonAddressDtoService personAddressService,
            TranslationService translationService,
            PersonAddressModelAssembler assembler) {
        
        super(personAddressService, translationService, assembler, "entity.personAddress");
        this.personAddressService = personAddressService;
        this.concreteAssembler = assembler;
    }

    
    @PostMapping
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonAddressDto>>>> 
    create(@RequestBody @Validated(OnCreate.class) PersonAddressDto dto) 
    {
        
        ServiceResult<PersonAddressDto> result = personAddressService.create(dto);
        
        if (result.isRejected()) {
            return buildConflictResponse(result);
        }
        
        return buildSuccessResponse(result, "crud.created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonAddressDto>>>> update(
            @PathVariable("id") PersonAddressDtoId id,
            @RequestBody @Validated(OnUpdate.class) PersonAddressDto dto) {
        
        ServiceResult<PersonAddressDto> result = personAddressService.update(dto, id);
        
        if (result.isRejected()) {
            return buildConflictResponse(result);
        }
        
        return buildSuccessResponse(result, "crud.updated", HttpStatus.OK);
    }

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonAddressDto>>>> buildConflictResponse(
            ServiceResult<PersonAddressDto> result) {
        
        PersonAddressDto rejectedDtoWithExistingId = result.value();
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

    private ResponseEntity<ApiSuccessResponse<ServiceResult<EntityModel<PersonAddressDto>>>> buildSuccessResponse(
            ServiceResult<PersonAddressDto> result, String translationKey, HttpStatus status) {
        
        EntityModel<PersonAddressDto> entityModel = concreteAssembler.toModel(result.value());
        ServiceResult<EntityModel<PersonAddressDto>> successServiceResult = ServiceResult.success(entityModel);
        String successMessage = translationService.translate(translationKey, getEntityName());
        
        return buildResponse(successServiceResult, successMessage, status);
    }

}