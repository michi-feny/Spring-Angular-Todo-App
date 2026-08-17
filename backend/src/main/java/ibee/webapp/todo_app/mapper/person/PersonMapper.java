package ibee.webapp.todo_app.mapper.person;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.Person;


@Mapper(config = MapStructConfig.class)
public interface PersonMapper {

}
