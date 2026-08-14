package ibee.webapp.todo_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.core.dto.PhoneNumberDto;
import ibee.webapp.todo_app.core.entity.PhoneNumber;

@Mapper(config = MapStructConfig.class)
public interface PhoneNumberMapper extends BaseMapper<PhoneNumberDto, PhoneNumber> {

    @Mapping(target = "fullNumber", expression = "java(entity.getCountryCode() + entity.getPhoneNumber())")
    PhoneNumberDto toDto(PhoneNumber entity);

    @Mapping(target = "fullNumber", ignore = true)
    PhoneNumber toEntity(PhoneNumberDto dto);
}
