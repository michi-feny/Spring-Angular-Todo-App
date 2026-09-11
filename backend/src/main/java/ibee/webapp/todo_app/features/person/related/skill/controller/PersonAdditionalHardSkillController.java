package ibee.webapp.todo_app.features.person.related.skill.controller;


import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkillId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonAdditionalHardSkillDtoId;
import ibee.webapp.todo_app.features.person.related.skill.controller.assembler.PersonAdditionalHardSkillModelAssembler;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonAdditionalHardSkillDto;
import ibee.webapp.todo_app.features.person.related.skill.service.PersonAdditionalSkillDtoService;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-additional-hard-skills")
public class PersonAdditionalHardSkillController 
    extends AbstractSpringPersonRelatedHateoasController<
        PersonAdditionalHardSkillDto,
        PersonAdditionalHardSkill,
        PersonAdditionalHardSkillId,
        PersonAdditionalHardSkillDtoId> {

    public PersonAdditionalHardSkillController(
            PersonAdditionalSkillDtoService service,
            TranslationService translationService,
            PersonAdditionalHardSkillModelAssembler assembler) {
        
        super(service, translationService, assembler, "entity.personAdditionalHardSkills");
    }
}