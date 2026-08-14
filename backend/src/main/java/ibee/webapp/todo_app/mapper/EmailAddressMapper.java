package ibee.webapp.todo_app.mapper;



import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.EmailAddressDto;
import ibee.webapp.todo_app.core.entity.EmailAddress;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

import org.mapstruct.Mapper;


@Mapper(
        config = MapStructConfig.class
)
public interface EmailAddressMapper
        extends BaseMapper<EmailAddressDto, EmailAddress> {

}