package ibee.webapp.todo_app.controller.person.related.skill.hard.additionalHardSkill;


import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-additional-hard-skills")
public class PersonAdditionalHardSkillController extends AbstractSpringPersonRelatedHateoasController<
        PersonAdditionalHardSkillDto,
        PersonAdditionalHardSkill,
        PersonAdditionalHardSkillId,
        PersonAdditionalHardSkillDtoId> {

    public PersonAdditionalHardSkillController(
            PersonRelatedDtoService<PersonAdditionalHardSkillDto, PersonAdditionalHardSkill, 
            PersonAdditionalHardSkillId, PersonAdditionalHardSkillDtoId> service,
            TranslationService translationService,
            PersonAdditionalHardSkillModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personAdditionalHardSkills");
    }
}