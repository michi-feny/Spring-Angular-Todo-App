package ibee.webapp.todo_app.features.person.related.skill.controller.assembler;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonDegreeDtoId;
import ibee.webapp.todo_app.features.person.related.skill.controller.PersonDegreeController;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonDegreeDto;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonDegreeReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonDegreeModelAssembler 
        extends AbstractHateoasAssembler<PersonDegreeDto, PersonDegreeDtoId> {

    private final PersonDegreeReferenceMapper referenceMapper;

    public PersonDegreeModelAssembler(PersonDegreeReferenceMapper referenceMapper) {
        super(PersonDegreeController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonDegreeDtoId extractId(PersonDegreeDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return (dto.id()); 
    }

    @Override
    public EntityModel<PersonDegreeDto> toModel(PersonDegreeDto dto) {
        return super.toModel(dto);
    }
}
