package ibee.webapp.todo_app.controller.person.related.contact.address;

import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.contact.address.PersonAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
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
public class PersonAddressController extends AbstractSpringPersonRelatedHateoasController<
        PersonAddressDto,
        PersonAddress,
        PersonAddressId,
        PersonAddressDtoId> {

    public PersonAddressController(
            PersonRelatedDtoService<PersonAddressDto, PersonAddress, PersonAddressId, PersonAddressDtoId> service,
            TranslationService translationService,
            PersonAddressModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personAddress");
    }
}