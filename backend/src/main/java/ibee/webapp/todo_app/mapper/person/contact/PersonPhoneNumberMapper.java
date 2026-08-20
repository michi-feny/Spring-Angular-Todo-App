package ibee.webapp.todo_app.mapper.person.contact;

import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.mapper.PhoneNumberMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import ibee.webapp.todo_app.mapper.person.references.contact.PersonPhoneNumberReferenceMapper;
import ibee.webapp.todo_app.core.dto.person.contact.phone.PersonPhoneNumberDto;
import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonPhoneNumberDtoId;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class,

    uses = {
        PhoneNumberMapper.class,
        PersonPhoneNumberReferenceMapper.class
    }
)
public interface PersonPhoneNumberMapper 
    extends BaseMapper<
        PersonPhoneNumberDto, PersonPhoneNumber> {

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target="phoneNumber", source="phoneNumber")
    @Mapping(target = "mainPhoneNumber", source = "mainPhoneNumber")
    PersonPhoneNumberDto toDto(PersonPhoneNumber entity);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person.id", source = "id.personId")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "mainPhoneNumber", source = "mainPhoneNumber")
    PersonPhoneNumber toEntity(PersonPhoneNumberDto dto);
/* 
    @Override
    default List<PersonPhoneNumberDto> toDtoList(List<PersonPhoneNumber> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).toList(); 
    }
//no explicit Override Needed, cause my BaseMapper already has a default implementation for this method
    // @Override
    // default List<PersonPhoneNumber> toEntityList(List<PersonPhoneNumberDto> dtos) {
    //     if (dtos == null) return null;
    //     return dtos.stream().map(this::toEntity).toList();
    // }
 /*   @Override
    default List<PersonPhoneNumber> toEntityList(List<PersonPhoneNumberDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).toList();
    }

    // UI id mappings (entity -> ui id) and (dto -> ui id). dto contains nested id record.
    @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "phoneNumberId", source = "id.phoneNumberId")
    PersonPhoneNumberDtoId toUiId(PersonPhoneNumber entity);
*/
   /* 
   //also not needed, cause ic an use my RefMapper direct!!!
   @Mapping(target = "personId", source = "id.personId")
    @Mapping(target = "phoneNumberId", source = "id.phoneNumberId")
    PersonPhoneNumberDtoId toUiId(PersonPhoneNumberDto dto);

    default List<PersonPhoneNumberDtoId> toUiIdListFromEntities(List<PersonPhoneNumber> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toUiId).toList();
    }

    default List<PersonPhoneNumberDtoId> toUiIdListFromDtos(List<PersonPhoneNumberDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toUiId).toList();
    }
*/


}
