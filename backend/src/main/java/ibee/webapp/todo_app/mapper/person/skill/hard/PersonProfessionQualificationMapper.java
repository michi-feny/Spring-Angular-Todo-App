package ibee.webapp.todo_app.mapper.person.skill.hard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.skills.hard.PersonProfessionQualificationDto;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.skills.hard.ProfessionQualificationMapper;
import ibee.webapp.todo_app.mapper.EducationInstitutionMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        ProfessionQualificationMapper.class,
        EducationInstitutionMapper.class
    }
)
public interface PersonProfessionQualificationMapper
    extends BaseMapper<PersonProfessionQualificationDto, PersonProfessionQualification> {

    @Override
    @Mapping(target = "id.personId", source = "id.personId")
    @Mapping(target = "id.professionQualificationId", source = "professionQualification.id")
    @Mapping(target = "id.educationInstitutionId", source = "educationInstitution.id")
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonProfessionQualificationDto toDto(PersonProfessionQualification entity);

    @Override
    @Mapping(target = "id.personId", source = "id.personId")
    @Mapping(target = "id.professionQualificationId", source = "professionQualification.id")
    @Mapping(target = "id.educationInstitutionId", source = "educationInstitution.id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "professionQualification.id", source = "id.professionQualificationId")
    @Mapping(target = "educationInstitution.id", source = "id.educationInstitutionId")
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    PersonProfessionQualification toEntity(PersonProfessionQualificationDto dto);
}
