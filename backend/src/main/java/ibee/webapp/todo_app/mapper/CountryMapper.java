package ibee.webapp.todo_app.mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.CountryDto;
import ibee.webapp.todo_app.core.entity.Country;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

import org.mapstruct.Mapper;


@Mapper(
        config = MapStructConfig.class
)
public interface CountryMapper
        extends BaseMapper<CountryDto, Country> {

}
