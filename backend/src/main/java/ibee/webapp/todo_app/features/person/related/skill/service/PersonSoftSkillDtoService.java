package ibee.webapp.todo_app.features.person.related.skill.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.repository.person.personRelated.skill.softSkill.PersonSoftSkillRepository;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractMappedPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.softSkill.PersonSoftSkillServiceImpl;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.features.person.related.skill.dto.soft.PersonSoftSkillDto;
import ibee.webapp.todo_app.features.person.related.workExp.dto.PersonWorkExperienceDto;
import ibee.webapp.todo_app.mapper.person.references.skill.soft.PersonSoftSkillReferenceMapper;
import ibee.webapp.todo_app.mapper.person.skill.soft.PersonSoftSkillMapper;


@Service
@Transactional
public class PersonSoftSkillDtoService
        extends AbstractMappedPersonRelatedDtoService<
            PersonSoftSkillDto,
            PersonSoftSkill,
            PersonSoftSkillId,
            PersonSoftSkillDtoId> 
{

    private final PersonSoftSkillRepository personsoftSKillRepository;
    private final PersonSoftSkillMapper mapper;
    public PersonSoftSkillDtoService(
            PersonSoftSkillRepository personProfessionQualificationRepository,
            PersonSoftSkillServiceImpl personEntityService,
            PersonSoftSkillMapper mapper,
            PersonSoftSkillReferenceMapper idReferenceMapper) {
        super(personEntityService, mapper, idReferenceMapper);
        this.personsoftSKillRepository = personProfessionQualificationRepository;
        this.mapper = mapper;
    }

    
}
