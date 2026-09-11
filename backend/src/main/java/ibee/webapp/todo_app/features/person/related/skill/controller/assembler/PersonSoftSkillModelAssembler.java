package ibee.webapp.todo_app.features.person.related.skill.controller.assembler;


import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.features.person.related.skill.controller.PersonSoftSkillController;
import ibee.webapp.todo_app.features.person.related.skill.dto.soft.PersonSoftSkillDto;
import ibee.webapp.todo_app.mapper.person.references.skill.soft.PersonSoftSkillReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonSoftSkillModelAssembler 
        extends AbstractHateoasAssembler<PersonSoftSkillDto, PersonSoftSkillDtoId> {

    private final PersonSoftSkillReferenceMapper referenceMapper;

    public PersonSoftSkillModelAssembler(PersonSoftSkillReferenceMapper referenceMapper) {
        super(PersonSoftSkillController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonSoftSkillDtoId extractId(PersonSoftSkillDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return (dto.id()); 
    }

    @Override
    public EntityModel<PersonSoftSkillDto> toModel(PersonSoftSkillDto dto) {
        return super.toModel(dto);
    }
}
