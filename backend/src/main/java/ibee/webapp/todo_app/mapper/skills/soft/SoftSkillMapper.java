package ibee.webapp.todo_app.mapper.skills.soft;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.SoftSkill;
import ibee.webapp.todo_app.dto.skills.hard.soft.SoftSkillDto;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface SoftSkillMapper extends BaseMapper<SoftSkillDto, SoftSkill> {

  /* 
    @Override
    SoftSkillDto toDto(SoftSkill entity);

    @Override
    SoftSkill toEntity(SoftSkillDto dto);

*/
}
