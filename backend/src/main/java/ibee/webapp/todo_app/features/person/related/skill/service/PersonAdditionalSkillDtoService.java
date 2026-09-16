package ibee.webapp.todo_app.features.person.related.skill.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractMappedPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.hardSkill.additionalHardSkill.PersonAdditionalHardSkillServiceImpl;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.features.person.related.workExp.dto.PersonWorkExperienceDto;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonAdditionalHardSkillReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.hard.PersonAdditionalHardSkillMapper;

@Service
@Transactional
public class PersonAdditionalSkillDtoService
        extends AbstractMappedPersonRelatedDtoService<
            PersonAdditionalHardSkillDto,
            PersonAdditionalHardSkill,
            PersonAdditionalHardSkillId,
            PersonAdditionalHardSkillDtoId> 
{

    public PersonAdditionalSkillDtoService(
            PersonAdditionalHardSkillServiceImpl personEntityService,
            PersonAdditionalHardSkillMapper mapper,
            PersonAdditionalHardSkillReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
    }

    
}
