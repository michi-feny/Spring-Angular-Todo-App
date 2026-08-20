package ibee.webapp.todo_app.mapper.person.skill.hard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.skill.hard.PersonProfessionQualificationReferenceMapper;
import ibee.webapp.todo_app.mapper.skills.hard.ProfessionQualificationMapper;
import ibee.webapp.todo_app.mapper.EducationInstitutionMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        ProfessionQualificationMapper.class,
        EducationInstitutionMapper.class,
        PersonProfessionQualificationReferenceMapper.class
    }
)
public interface PersonProfessionQualificationMapper
    extends BaseMapper<PersonProfessionQualificationDto, PersonProfessionQualification> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonProfessionQualificationDto toDto(PersonProfessionQualification entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonProfessionQualification toEntity(PersonProfessionQualificationDto dto);
}
