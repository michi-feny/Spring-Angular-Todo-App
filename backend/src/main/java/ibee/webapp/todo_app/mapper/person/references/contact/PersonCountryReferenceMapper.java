package ibee.webapp.todo_app.mapper.person.references.contact;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonCountryReferenceMapper 
    extends BaseMapper<PersonCountryDtoId, PersonCountryId> {

    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "countryId", source = "countryId")
    @Override
    PersonCountryDtoId toDto(PersonCountryId id);

    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "countryId", source = "countryId")
    @Override
    PersonCountryId toEntity(PersonCountryDtoId dto);

    @Override
    List<PersonCountryDtoId> toDtoList(List<PersonCountryId> entities);

    @Override
    List<PersonCountryId> toEntityList(List<PersonCountryDtoId> dtos);
   
}