package ibee.webapp.todo_app.controller.person.related.skill.hard.additionalHardSkill;

import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonAdditionalHardSkillReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonAdditionalHardSkillModelAssembler 
        extends AbstractHateoasAssembler<PersonAdditionalHardSkillDto, PersonAdditionalHardSkillId> {

    private final PersonAdditionalHardSkillReferenceMapper referenceMapper;

    public PersonAdditionalHardSkillModelAssembler(PersonAdditionalHardSkillReferenceMapper referenceMapper) {
        super(PersonAdditionalHardSkillController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonAdditionalHardSkillId extractId(PersonAdditionalHardSkillDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return referenceMapper.toEntity(dto.id()); 
    }

    @Override
    public EntityModel<PersonAdditionalHardSkillDto> toModel(PersonAdditionalHardSkillDto dto) {
        return super.toModel(dto);
    }
}