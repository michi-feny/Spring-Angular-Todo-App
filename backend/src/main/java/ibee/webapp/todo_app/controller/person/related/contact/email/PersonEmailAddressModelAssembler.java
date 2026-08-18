package ibee.webapp.todo_app.controller.person.related.contact.email;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.contact.mail.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonEmailAddressReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonEmailAddressModelAssembler 
        extends AbstractHateoasAssembler<PersonEmailAddressDto, PersonEmailAddressId> {

    private final PersonEmailAddressReferenceMapper referenceMapper;

    public PersonEmailAddressModelAssembler(PersonEmailAddressReferenceMapper referenceMapper) {
        super(PersonEmailAddressController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonEmailAddressId extractId(PersonEmailAddressDto dto) {
        // Uses the record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return referenceMapper.toEntity(dto.id()); 
    }

    @Override
    public EntityModel<PersonEmailAddressDto> toModel(PersonEmailAddressDto dto) {
        return super.toModel(dto);
    }
}