package ibee.webapp.todo_app.mapper.person.references.contact;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonEmailAddressDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddressId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonEmailReferenceMapper 
    extends BaseMapper<PersonEmailAddressDtoId, PersonEmailAddressId> {
    
  @Override
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "emailAddressId", source = "emailAddressId")
    PersonEmailAddressDtoId toDto(PersonEmailAddressId entity);

    @Override
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "emailAddressId", source = "emailAddressId")
    PersonEmailAddressId toEntity(PersonEmailAddressDtoId dto);

    @Override
    List<PersonEmailAddressDtoId> toDtoList(List<PersonEmailAddressId> entities);

    @Override
    List<PersonEmailAddressId> toEntityList(List<PersonEmailAddressDtoId> dtos);

    
}
