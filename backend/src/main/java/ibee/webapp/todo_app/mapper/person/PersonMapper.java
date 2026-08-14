package ibee.webapp.todo_app.mapper.person;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.PersonCreate;
import ibee.webapp.todo_app.core.dto.person.PersonData;
import ibee.webapp.todo_app.core.dto.person.PersonUpdate;
import ibee.webapp.todo_app.core.entity.Person;


@Mapper(config = MapStructConfig.class)
public interface PersonMapper {

    Person toEntity(PersonCreate model);

    PersonData toModel(Person entity);

    void updateEntity(
            PersonUpdate model,
            @MappingTarget Person entity
    );
}
