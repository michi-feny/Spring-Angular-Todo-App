package ibee.webapp.todo_app.mapper;

import ibee.webapp.todo_app.core.dto.address.CreateAddressDto;
import ibee.webapp.todo_app.core.entity.Address;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.config.MapStructConfig;

import org.mapstruct.Mapper;



@Mapper(
        config = MapStructConfig.class,
        uses = {
                CountryMapper.class
        }
)
public interface AddressMapper
        extends BaseMapper<CreateAddressDto, Address> {


}
