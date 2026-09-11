package ibee.webapp.todo_app.features.person.related.skill.controller.assembler;


import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.features.person.related.referenceIds.skill.hard.PersonProfessionQualificationDtoId;
import ibee.webapp.todo_app.features.person.related.skill.controller.PersonProfessionQualificationController;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonProfessionQualificationReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonProfessionQualificationModelAssembler 
        extends AbstractHateoasAssembler<PersonProfessionQualificationDto, PersonProfessionQualificationDtoId> {

    private final PersonProfessionQualificationReferenceMapper referenceMapper;

    public PersonProfessionQualificationModelAssembler(PersonProfessionQualificationReferenceMapper referenceMapper) {
        super(PersonProfessionQualificationController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonProfessionQualificationDtoId extractId(PersonProfessionQualificationDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return dto.id(); 
    }

    @Override
    public EntityModel<PersonProfessionQualificationDto> toModel(PersonProfessionQualificationDto dto) {
        return super.toModel(dto);
    }
}
