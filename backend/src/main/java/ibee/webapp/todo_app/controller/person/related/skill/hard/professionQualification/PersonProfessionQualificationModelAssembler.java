package ibee.webapp.todo_app.controller.person.related.skill.hard.professionQualification;


import ibee.webapp.todo_app.controller.support.hateoas.assembler.AbstractHateoasAssembler;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualificationId;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonProfessionQualificationReferenceMapper;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class PersonProfessionQualificationModelAssembler 
        extends AbstractHateoasAssembler<PersonProfessionQualificationDto, PersonProfessionQualificationId> {

    private final PersonProfessionQualificationReferenceMapper referenceMapper;

    public PersonProfessionQualificationModelAssembler(PersonProfessionQualificationReferenceMapper referenceMapper) {
        super(PersonProfessionQualificationController.class);
        this.referenceMapper = referenceMapper;
    }

    @Override
    protected PersonProfessionQualificationId extractId(PersonProfessionQualificationDto dto) {
        // Uses record accessor 'dto.id()' and maps the DTO ID to the Entity ID
        return referenceMapper.toEntity(dto.id()); 
    }

    @Override
    public EntityModel<PersonProfessionQualificationDto> toModel(PersonProfessionQualificationDto dto) {
        return super.toModel(dto);
    }
}
