package ibee.webapp.todo_app.features.person.related.skill.controller.assembler;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.features.person.related.skill.controller.PersonAdditionalHardSkillController;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonAdditionalHardSkillReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonAdditionalHardSkillModelAssembler 
        extends AbstractHateoasAssembler<PersonAdditionalHardSkillDto, PersonAdditionalHardSkillDtoId> {

    private final PersonAdditionalHardSkillReferenceMapper referenceMapper;

    public PersonAdditionalHardSkillModelAssembler(PersonAdditionalHardSkillReferenceMapper referenceMapper) {
        super(PersonAdditionalHardSkillController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonAdditionalHardSkillDtoId extractId(PersonAdditionalHardSkillDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return (dto.id()); 
    }

    @Override
    public EntityModel<PersonAdditionalHardSkillDto> toModel(PersonAdditionalHardSkillDto dto) {
        return super.toModel(dto);
    }
}