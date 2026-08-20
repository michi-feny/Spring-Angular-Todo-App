package ibee.webapp.todo_app.mapper.person.contact;



import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.contact.mail.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.mapper.EmailAddressMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonEmailAddressReferenceMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(
    config = MapStructConfig.class,
    uses = {  
        EmailAddressMapper.class,
        PersonEmailAddressReferenceMapper.class
    }
)
public interface PersonEmailAddressMapper extends BaseMapper<PersonEmailAddressDto, PersonEmailAddress> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "emailAddress", source = "emailAddress")
    @Mapping(target = "mainEmail", source = "mainEmail")
    PersonEmailAddressDto toDto(PersonEmailAddress entity);

/*
private Long personId;
PersonEmailAddress id
    @Column(name = "email_address_id")
    private Long emailAddressId;
    private Long personId;
*/

/*
PersonEmailAddressDto id

    Long emailAddressId,

    @NotNull
    @Positive
    Long personId
*/

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "emailAddress", source = "emailAddress")
    @Mapping(target = "mainEmail", source = "mainEmail")
    PersonEmailAddress toEntity(PersonEmailAddressDto dto);

    /* 
    @Override
    default List<PersonEmailAddressDto> toDtoList(List<PersonEmailAddress> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    default List<PersonEmailAddress> toEntityList(List<PersonEmailAddressDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
            .map(this::toEntity)
            .toList();
    }

    // element mappings to UiId
    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "emailAddressId", source = "id.emailAddressId")
    PersonEmailAddressDtoId toUiId(PersonEmailAddress entity);

    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "emailAddressId", source = "id.emailAddressId")
    PersonEmailAddressDtoId toUiId(PersonEmailAddressDto dto);

    default List<PersonEmailAddressDtoId> toUiIdListFromEntities(List<PersonEmailAddress> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
            .map(this::toUiId)
            .toList();
    }

    default List<PersonEmailAddressDtoId> toUiIdListFromDtos(List<PersonEmailAddressDto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
            .map(this::toUiId)
            .toList();
    }
*/
}