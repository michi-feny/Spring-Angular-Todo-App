package ibee.webapp.todo_app.mapper.person.contact;

import java.util.List;

import org.mapstruct.Mapper;

import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonAddressUiId;
import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.mapper.AddressMapper;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        AddressMapper.class
    }
)
public interface PersonAddressMapper {
    PersonAddressUiId toReference(PersonAddress entity);

    List<PersonAddressUiId> toReferenceList(
            List<PersonAddress> entities
    );


}
