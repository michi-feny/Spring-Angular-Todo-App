package ibee.webapp.todo_app.controller.person.related.contact.phone;


import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.contact.phone.PersonPhoneNumberDto;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumberId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneNumberReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
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
        extends AbstractHateoasAssembler<PersonPhoneNumberDto, PersonPhoneNumberId> {

    private final PersonPhoneNumberReferenceMapper referenceMapper;

    public PersonPhoneNumberModelAssembler(PersonPhoneNumberReferenceMapper referenceMapper) {
        super(PersonPhoneNumberController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonPhoneNumberId extractId(PersonPhoneNumberDto dto) {
        // Uses the record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return referenceMapper.toEntity(dto.id()); 
    }

    @Override
    public EntityModel<PersonPhoneNumberDto> toModel(PersonPhoneNumberDto dto) {
        return super.toModel(dto);
    }
}
