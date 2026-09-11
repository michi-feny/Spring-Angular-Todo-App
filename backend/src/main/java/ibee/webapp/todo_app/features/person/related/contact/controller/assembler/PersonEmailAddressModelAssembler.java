package ibee.webapp.todo_app.features.person.related.contact.controller.assembler;

import static ibee.webapp.todo_app.controller.support.hateoas.LinkConverter.LinkUtils.createCustomLink;

import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.features.person.related.contact.controller.PersonEmailAddressController;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonEmailAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonEmailAddressReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PersonEmailAddressModelAssembler 
        extends AbstractHateoasAssembler<PersonEmailAddressDto, PersonEmailAddressDtoId> {

    private final PersonEmailAddressReferenceMapper referenceMapper;

    public PersonEmailAddressModelAssembler(PersonEmailAddressReferenceMapper referenceMapper) {
        super(PersonEmailAddressController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonEmailAddressDtoId extractId(PersonEmailAddressDto dto) {
        // Uses the record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        Assert.notNull(dto, "PersonEmailDto is not allowed to be Null");
        Assert.notNull(dto.id(), "the ID of PersonEmailAddressDto is not allowed to be Null");
        return dto.id(); 
    }

    @Override
    public EntityModel<PersonEmailAddressDto> toModel(PersonEmailAddressDto dto) {
        return super.toModel(dto);
    }

    
    public Link getRepairLinkForUpdate(PersonEmailAddressDtoId existingId) {
        Assert.notNull(existingId, "Existing ID cannot be null");
        Assert.notNull(existingId.personId(), "Existing personId cannot be null");
        Assert.notNull(existingId.emailAddressId(), "Existing emailId cannot be null");

        // Format is "personId_emailId"
        String pathIdString = existingId.personId() + "_" + existingId.emailAddressId();
        return createCustomLink(getController(), "repair", pathIdString, "PUT");
    }
}