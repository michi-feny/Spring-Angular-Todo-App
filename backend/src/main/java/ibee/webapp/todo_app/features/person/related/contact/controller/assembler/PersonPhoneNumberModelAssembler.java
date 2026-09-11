package ibee.webapp.todo_app.features.person.related.contact.controller.assembler;

import static ibee.webapp.todo_app.controller.support.hateoas.LinkConverter.LinkUtils.createCustomLink;
import ibee.webapp.todo_app.controller.support.Link;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.features.person.related.contact.controller.PersonPhoneNumberController;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonPhoneNumberDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonPhoneNumberDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneNumberReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
/*

Because PersonPhoneNumberController inherits from AbstractSpringPersonRelatedHateoasController, which in turn extends AbstractSpringHateoasCrudController, it comes packed with a robust set of both standard CRUD operations and custom person-related endpoints.

Here is everything that PersonPhoneNumberController (and all other person-related controllers like Address and Email) can do:

1. Standard CRUD Endpoints (Inherited from AbstractSpringHateoasCrudController)
Get All: GET /api/v1/person-phone-numbers

Retrieves a HATEOAS collection model of all phone numbers in the system wrapped in an ApiSuccessResponse.

Get By ID: GET /api/v1/person-phone-numbers/{id}

Retrieves a single phone number entity model by its primary ID, complete with HATEOAS links (self, update, delete, list).

Create: POST /api/v1/person-phone-numbers

Accepts a validated PersonPhoneNumberDto request body, saves it, and returns the newly created resource model with a 201 CREATED status.

Update: PUT /api/v1/person-phone-numbers/{id}

Updates an existing phone number matching the path ID with the provided request body DTO.

Delete: DELETE /api/v1/person-phone-numbers/{id}

Deletes the phone number matching the given ID and returns a successful empty response.

2. Person-Specific Endpoints (Inherited from AbstractSpringPersonRelatedHateoasController)
Get All By Person ID: GET /api/v1/person-phone-numbers/person/{personId}

Retrieves a collection model of all phone numbers tied directly to a specific person's ID.

Get Reference IDs By Person ID: GET /api/v1/person-phone-numbers/person/{personId}/ids

Retrieves a lightweight list of reference ID DTOs (PersonPhoneNumberDtoId) for a given person.

Get With Details By ID: GET /api/v1/person-phone-numbers/{id}/details

Fetches an extended, detailed view of a specific phone number entity model.
 */
@Component
public class PersonPhoneNumberModelAssembler 
        extends AbstractHateoasAssembler<PersonPhoneNumberDto, PersonPhoneNumberDtoId> {

    private final PersonPhoneNumberReferenceMapper referenceMapper;

    public PersonPhoneNumberModelAssembler(PersonPhoneNumberReferenceMapper referenceMapper) {
        super(PersonPhoneNumberController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonPhoneNumberDtoId extractId(PersonPhoneNumberDto dto) {
        Assert.notNull(dto, "the PersonPhoneNumberDto is not allowed to be null");
        Assert.notNull(dto.id(), "the composite Key of PersonPhoneNumberDto is not allowed to be null!");
        Assert.notNull(dto.id().personId(), "The personId component cannot be null!");
        Assert.notNull(dto.id().phoneNumberId(), "The phoneId component cannot be null!");
        return dto.id();
    }

    @Override
    public EntityModel<PersonPhoneNumberDto> toModel(PersonPhoneNumberDto dto) {
        return super.toModel(dto);
    }

    public Link getRepairLinkForUpdate(PersonPhoneNumberDtoId existingId) {
        Assert.notNull(existingId, "Existing ID cannot be null");
        Assert.notNull(existingId.personId(), "Existing personId cannot be null");
        Assert.notNull(existingId.phoneNumberId(), "Existing phoneId cannot be null");

        String pathIdString = existingId.personId() + "_" + existingId.phoneNumberId();
        return createCustomLink(getController(), "repair", pathIdString, "PUT");
    }
}
