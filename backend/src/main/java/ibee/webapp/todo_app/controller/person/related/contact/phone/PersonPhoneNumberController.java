package ibee.webapp.todo_app.controller.person.related.contact.phone;

import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.contact.phone.PersonPhoneNumberDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
PersonPhoneNumberController
Base Route: /api/v1/person-phone-numbers

1. Standard CRUD Endpoints (Inherited from AbstractSpringHateoasCrudController)
Get All: GET /api/v1/person-phone-numbers

Retrieves a HATEOAS collection model of all person phone numbers in the system inside an ApiSuccessResponse.

Get By ID: GET /api/v1/person-phone-numbers/{id}

Retrieves a single phone number entity model by its primary ID, complete with automatic HATEOAS links (self, update, delete, list).

Create: POST /api/v1/person-phone-numbers

Accepts a validated PersonPhoneNumberDto request body, saves it, and returns the newly created resource model with a 201 CREATED status.

Update: PUT /api/v1/person-phone-numbers/{id}

Updates an existing phone number record matching the path ID with the provided request body DTO.

Delete: DELETE /api/v1/person-phone-numbers/{id}

Deletes the phone number record matching the given ID and returns a successful empty response.

2. Person-Specific Endpoints (Inherited from AbstractSpringPersonRelatedHateoasController)
Get All By Person ID: GET /api/v1/person-phone-numbers/person/{personId}

Retrieves a collection model of all phone number records tied directly to a specific person's ID.

Get Reference IDs By Person ID: GET /api/v1/person-phone-numbers/person/{personId}/ids

Retrieves a lightweight list of reference ID DTOs (PersonPhoneNumberDtoId) for a given person.

Get With Details By ID: GET /api/v1/person-phone-numbers/{id}/details

Fetches an extended, detailed view of a specific phone number entity model.

*/
@RestController
@RequestMapping("/api/v1/person-phone-numbers")
public class PersonPhoneNumberController extends AbstractSpringPersonRelatedHateoasController<
        PersonPhoneNumberDto,
        PersonPhoneNumber,
        PersonPhoneNumberId,
        PersonPhoneNumberDtoId> {

    public PersonPhoneNumberController(
            PersonRelatedDtoService<PersonPhoneNumberDto, PersonPhoneNumber, PersonPhoneNumberId, PersonPhoneNumberDtoId> service,
            TranslationService translationService,
            PersonPhoneNumberModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personPhoneNumber");
    }
}
