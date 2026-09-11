package ibee.webapp.todo_app.mapper.person.skill.hard;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.features.person.related.skill.dto.hard.PersonProfessionQualificationDto;
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
    @Mapping(
        target = "professionQualificationDuration.startDate", 
        source = "startDate"
    ) // Packing into DurationDto
    @Mapping(
        target = "professionQualificationDuration.endDate", 
        source = "endDate")
    PersonProfessionQualificationDto toDto(PersonProfessionQualification entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    @Mapping(
        target = "startDate",
        source = "professionQualificationDuration.startDate"
    ) // Packing into DurationDto
    @Mapping(
        target = "endDate",
        source = "professionQualificationDuration.endDate" 
    )
    PersonProfessionQualification toEntity(PersonProfessionQualificationDto dto);

    // --- 3. DTO UPDATE (UI -> DB) ---
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    @Mapping(
        target = "startDate",
        source = "professionQualificationDuration.startDate"
    ) // Packing into DurationDto
    @Mapping(
        target = "endDate",
        source = "professionQualificationDuration.endDate" 
    )
    void updateEntityFromDto(PersonProfessionQualificationDto dto, @MappingTarget PersonProfessionQualification entity);

    // --- 4. INTERNAL UPDATE ---
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "professionQualification", source = "professionQualification")
    @Mapping(target = "educationInstitution", source = "educationInstitution")
    void updateEntityFromEntity(PersonProfessionQualification sourceUpdates, @MappingTarget PersonProfessionQualification dbEntity);
}
