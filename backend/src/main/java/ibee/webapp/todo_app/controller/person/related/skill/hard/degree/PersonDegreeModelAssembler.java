package ibee.webapp.todo_app.controller.person.related.skill.hard.degree;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonDegreeDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegreeId;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonDegreeReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonDegreeModelAssembler 
        extends AbstractHateoasAssembler<PersonDegreeDto, PersonDegreeId> {

    private final PersonDegreeReferenceMapper referenceMapper;

    public PersonDegreeModelAssembler(PersonDegreeReferenceMapper referenceMapper) {
        super(PersonDegreeController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonDegreeId extractId(PersonDegreeDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return referenceMapper.toEntity(dto.id()); 
    }

    @Override
    public EntityModel<PersonDegreeDto> toModel(PersonDegreeDto dto) {
        return super.toModel(dto);
    }
}
