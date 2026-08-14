package ibee.webapp.todo_app.mapper;
import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.DegreeDto;
import ibee.webapp.todo_app.core.entity.hardSkills.Degree;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

import org.mapstruct.Mapper;
@Mapper(
    config = MapStructConfig.class
)
public interface DegreeMapper extends BaseMapper<DegreeDto, Degree>{

}
