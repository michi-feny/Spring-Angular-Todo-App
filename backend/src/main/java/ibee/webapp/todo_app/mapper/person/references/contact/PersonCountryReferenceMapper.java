package ibee.webapp.todo_app.mapper.person.references.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryReference;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountryId;

@Mapper(config = MapStructConfig.class)
public interface PersonCountryReferenceMapper {

    @Mapping(
        target = "countryId",
        source = "countryId"
    )
    PersonCountryReference toModel(PersonCountryId id);
}