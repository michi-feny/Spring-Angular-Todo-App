package ibee.webapp.todo_app.features.person.related.contact.controller;


import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.features.person.related.contact.PersonAddressDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PersonAddressModelAssembler 
        extends AbstractHateoasAssembler<PersonAddressDto, PersonAddressId> {


    private final PersonAddressReferenceMapper referenceMapper;

    public PersonAddressModelAssembler(PersonAddressReferenceMapper referenceMapper) {
        super(PersonAddressController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonAddressId extractId(PersonAddressDto dto) {
        // Record accessor syntax (e.g., dto.id() instead of dto.getId())
        Assert.notNull(dto, "the PersonAddressDto is not allowed to be null");
        Assert.notNull(dto.id(), "the composite Key of PersonAddressDto is not allowed to be null!");
        Assert.notNull(dto.id().personId(), "The personId component cannot be null!");
        Assert.notNull(dto.id().addressId(), "The addressId component cannot be null!");

        PersonAddressId entityId = referenceMapper.toEntity(dto.id());

        Assert.notNull(entityId, "The mapped PersonAddressId must not be null!");
        Assert.notNull(entityId.getPersonId(), "The mapped PersonAddressId.personId must not be null!");
        Assert.notNull(entityId.getAddressId(), "The mapped PersonAddressId.addressId must not be null!");

        return entityId;
    }

    @Override
    public EntityModel<PersonAddressDto> toModel(PersonAddressDto dto) {
         Assert.notNull(dto, "the PersonAddressDto is not allowed to be null");
        // Generates the standard links (self, update, delete, list)
        EntityModel<PersonAddressDto> model = super.toModel(dto);
        return model;
    }
}
