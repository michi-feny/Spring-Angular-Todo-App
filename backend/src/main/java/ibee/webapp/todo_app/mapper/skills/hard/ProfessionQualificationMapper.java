package ibee.webapp.todo_app.mapper.skills.hard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.core.dto.skills.hard.ProfessionQualificationDto;
import ibee.webapp.todo_app.core.dto.skills.hard.SkillType;
import ibee.webapp.todo_app.core.entity.hardSkills.ProfessionQualification;

@Mapper(
    config = MapStructConfig.class,
    imports = { SkillType.class }
)
public interface ProfessionQualificationMapper extends BaseMapper<ProfessionQualificationDto, ProfessionQualification> {

    @Mapping(target = "skillType", expression = "java(SkillType.PROFESSION_QUALIFICATION)")
    ProfessionQualificationDto toDto(ProfessionQualification entity);

    @Mapping(target = "skillType", ignore = true)
    ProfessionQualification toEntity(ProfessionQualificationDto dto);
}
