package ibee.webapp.todo_app.controller.person.related.skill.soft;


import ibee.webapp.todo_app.controller.person.related.AbstractSpringPersonRelatedHateoasController;
import ibee.webapp.todo_app.core.dto.person.skills.soft.PersonSoftSkillDto;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkillId;
import ibee.webapp.todo_app.core.service.person.related.PersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.skill.softSkill.PersonSoftSkillDtoService;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.soft.PersonSoftSkillDtoId;
import ibee.webapp.todo_app.infrastructure.i18n.TranslationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-soft-skills")
public class PersonSoftSkillController extends AbstractSpringPersonRelatedHateoasController<
        PersonSoftSkillDto,
        PersonSoftSkill,
        PersonSoftSkillId,
        PersonSoftSkillDtoId> {

    @SuppressWarnings("unchecked")
    public PersonSoftSkillController(
            PersonSoftSkillDtoService service,
            TranslationService translationService,
            PersonSoftSkillModelAssembler assembler) {
        
        super((PersonRelatedDtoService<PersonSoftSkillDto, PersonSoftSkill, PersonSoftSkillId, PersonSoftSkillDtoId>)service, translationService, assembler, "entity.personSoftSkill");
    }
}
