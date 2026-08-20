package ibee.webapp.todo_app.mapper.skills.hard;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.core.dto.skills.hard.DegreeDto;
import ibee.webapp.todo_app.core.dto.skills.hard.SkillType;
import ibee.webapp.todo_app.core.entity.hardSkills.Degree;

@Mapper(
    config = MapStructConfig.class,
    imports = { SkillType.class }
)
public interface DegreeMapper extends BaseMapper<DegreeDto, Degree> {

    @Mapping(target = "skillType", expression = "java(SkillType.DEGREE)")
    DegreeDto toDto(Degree entity);

    
    Degree toEntity(DegreeDto dto);
}
