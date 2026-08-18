package ibee.webapp.todo_app.controller.person.related.contact.country;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.contact.country.PersonCountryDto;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonCountryReferenceMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonCountryModelAssembler 
        extends AbstractHateoasAssembler<PersonCountryDto, PersonCountryId> {

    private final PersonCountryReferenceMapper referenceMapper;

    public PersonCountryModelAssembler(PersonCountryReferenceMapper referenceMapper) {
        super(PersonCountryController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonCountryId extractId(PersonCountryDto dto) {
        // Uses the record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return referenceMapper.toEntity(dto.id()); 
    }

    @Override
    public EntityModel<PersonCountryDto> toModel(PersonCountryDto dto) {
        return super.toModel(dto);
    }
}
