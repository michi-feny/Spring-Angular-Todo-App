package ibee.webapp.todo_app.controller.person.related.contact.address;


import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.contact.address.PersonAddressDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddressId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonAddressReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

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
        return referenceMapper.toEntity(dto.id());
    }

    @Override
    public EntityModel<PersonAddressDto> toModel(PersonAddressDto dto) {
        // Generates the standard links (self, update, delete, list)
        EntityModel<PersonAddressDto> model = super.toModel(dto);
        
        // If you need any specific child-level links for addresses, you can add them here!
        
        return model;
    }
}
