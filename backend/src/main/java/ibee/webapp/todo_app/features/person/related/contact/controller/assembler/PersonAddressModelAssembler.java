package ibee.webapp.todo_app.features.person.related.contact.controller.assembler;

import static ibee.webapp.todo_app.controller.support.hateoas.LinkConverter.LinkUtils.createCustomLink;

import ibee.webapp.todo_app.controller.support.Link;
import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.features.person.related.contact.controller.PersonAddressController;
import ibee.webapp.todo_app.features.person.related.contact.dto.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PersonAddressModelAssembler 
        extends AbstractHateoasAssembler<PersonAddressDto, PersonAddressDtoId> {


    private final PersonAddressReferenceMapper referenceMapper;

    public PersonAddressModelAssembler(PersonAddressReferenceMapper referenceMapper) {
        super(PersonAddressController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonAddressDtoId extractId(PersonAddressDto dto) {
        // Record accessor syntax (e.g., dto.id() instead of dto.getId())
        Assert.notNull(dto, "the PersonAddressDto is not allowed to be null");
        Assert.notNull(dto.id(), "the composite Key of PersonAddressDto is not allowed to be null!");
        Assert.notNull(dto.id().personId(), "The personId component cannot be null!");
        Assert.notNull(dto.id().addressId(), "The addressId component cannot be null!");

        // PersonAddressId entityId = referenceMapper.toEntity(dto.id());

        // Assert.notNull(entityId, "The mapped PersonAddressId must not be null!");
        // Assert.notNull(entityId.getPersonId(), "The mapped PersonAddressId.personId must not be null!");
        // Assert.notNull(entityId.getAddressId(), "The mapped PersonAddressId.addressId must not be null!");

        //return entityId;
        return dto.id();
    }

    @Override
    public EntityModel<PersonAddressDto> toModel(PersonAddressDto dto) {
         Assert.notNull(dto, "the PersonAddressDto is not allowed to be null");
        // Generates the standard links (self, update, delete, list)
        EntityModel<PersonAddressDto> model = super.toModel(dto);
        return model;
    }

    public Link getRepairLinkForUpdate(PersonAddressDtoId existingId) {
        Assert.notNull(existingId, "Existing ID cannot be null");
        Assert.notNull(existingId.personId(), "Existing personId cannot be null");
        Assert.notNull(existingId.addressId(), "Existing addressId cannot be null");

        // explicitly format the ID to match what DynamicRecordConverter expects (e.g., "1_2")
        String pathIdString = existingId.personId() + "_" + existingId.addressId();
        
        // Pass the pre-formatted string to slash() and return the Link
     // 1. Generate the native Spring HATEOAS link
        return createCustomLink(getController(), "repair", pathIdString, "PUT");

    }
}
