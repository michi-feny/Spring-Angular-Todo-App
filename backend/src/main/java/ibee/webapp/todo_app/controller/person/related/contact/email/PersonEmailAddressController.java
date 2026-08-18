package ibee.webapp.todo_app.controller.person.related.contact.email;

import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.contact.mail.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
PersonEmailAddressController
Base Route: /api/v1/person-email-addresses

1. Standard CRUD Endpoints (Inherited from AbstractSpringHateoasCrudController)
Get All: GET /api/v1/person-email-addresses

Retrieves a HATEOAS collection model of all person email addresses in the system inside an ApiSuccessResponse.

Get By ID: GET /api/v1/person-email-addresses/{id}

Retrieves a single email address entity model by its primary ID, complete with automatic HATEOAS links (self, update, delete, list).

Create: POST /api/v1/person-email-addresses

Accepts a validated PersonEmailAddressDto request body, saves it, and returns the newly created resource model with a 201 CREATED status.

Update: PUT /api/v1/person-email-addresses/{id}

Updates an existing email address record matching the path ID with the provided request body DTO.

Delete: DELETE /api/v1/person-email-addresses/{id}

Deletes the email address record matching the given ID and returns a successful empty response.

2. Person-Specific Endpoints (Inherited from AbstractSpringPersonRelatedHateoasController)
Get All By Person ID: GET /api/v1/person-email-addresses/person/{personId}

Retrieves a collection model of all email address records tied directly to a specific person's ID.

Get Reference IDs By Person ID: GET /api/v1/person-email-addresses/person/{personId}/ids

Retrieves a lightweight list of reference ID DTOs (PersonEmailAddressDtoId) for a given person.

Get With Details By ID: GET /api/v1/person-email-addresses/{id}/details

Fetches an extended, detailed view of a specific email address entity model.
*/
@RestController
@RequestMapping("/api/v1/person-email-addresses")
public class PersonEmailAddressController extends AbstractSpringPersonRelatedHateoasController<
        PersonEmailAddressDto,
        PersonEmailAddress,
        PersonEmailAddressId,
        PersonEmailAddressDtoId> {

    public PersonEmailAddressController(
            PersonRelatedDtoService<PersonEmailAddressDto, PersonEmailAddress, PersonEmailAddressId, PersonEmailAddressDtoId> service,
            TranslationService translationService,
            PersonEmailAddressModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personEmailAddress");
    }
}
