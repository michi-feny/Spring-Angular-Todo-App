package ibee.webapp.todo_app.features.person.related.contact.controller.assembler;
import static ibee.webapp.todo_app.controller.support.hateoas.LinkConverter.LinkUtils.createCustomLink;

import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.features.person.related.contact.controller.PersonCountryController;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonCountryDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonCountryReferenceMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PersonCountryModelAssembler 
        extends AbstractHateoasAssembler<PersonCountryDto, PersonCountryDtoId> {

    private final PersonCountryReferenceMapper referenceMapper;

    public PersonCountryModelAssembler(PersonCountryReferenceMapper referenceMapper) {
        super(PersonCountryController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonCountryDtoId extractId(PersonCountryDto dto) {
        // Uses the record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        Assert.notNull(dto, "PersonCountryDto is not allowed to be Null");
        Assert.notNull(dto.id(), "the ID of PersonCountryDto is not allowed to be Null");
        return dto.id(); 
    }

    @Override
    public EntityModel<PersonCountryDto> toModel(PersonCountryDto dto) {
        return super.toModel(dto);
    }

    public Link getRepairLinkForUpdate(PersonCountryDtoId existingId) {
        Assert.notNull(existingId, "Existing ID cannot be null");
        Assert.notNull(existingId.personId(), "Existing personId cannot be null");
        Assert.notNull(existingId.countryId(), "Existing countryId cannot be null");

        // Format is "personId_countryId"
        String pathIdString = existingId.personId() + "_" + existingId.countryId();
        return createCustomLink(getController(), "repair", pathIdString, "PUT");
    }
}
