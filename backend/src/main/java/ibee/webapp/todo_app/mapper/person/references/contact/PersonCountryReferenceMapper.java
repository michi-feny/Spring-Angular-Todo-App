package ibee.webapp.todo_app.mapper.person.references.contact;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;

@Mapper(config = MapStructConfig.class)
public interface PersonCountryReferenceMapper 
    extends BaseMapper<PersonCountryDtoId, PersonCountryId> {

    @Override
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "countryId", source = "countryId")
    PersonCountryDtoId toDto(PersonCountryId id);

    @Override
    @Mapping(target = "personId", source = "personId")
    @Mapping(target = "countryId", source = "countryId")
    PersonCountryId toEntity(PersonCountryDtoId dto);

   
}