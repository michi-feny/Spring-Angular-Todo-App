package ibee.webapp.todo_app.mapper.person.contact;



import ibee.webapp.todo_app.config.MapStructConfig;
import ibee.webapp.todo_app.core.dto.PersonEmailAddressDto;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAdress;
import ibee.webapp.todo_app.mapper.EmailAddressMapper;
import ibee.webapp.todo_app.mapper.baseMaper.BaseMapper;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Named;



@Mapper(
        config = MapStructConfig.class,
        uses = {
                EmailAddressMapper.class
        }
)
public interface PersonEmaildAdressMapper
        extends BaseMapper<PersonEmailAddressDto, PersonEmailAdress> {

        @Override
        PersonEmailAddressDto toDto(PersonEmailAdress entity);

        @Override
        PersonEmailAdress toEntity(PersonEmailAddressDto dto);


        @Override
        List<PersonEmailAddressDto> toDtoList(List<PersonEmailAdress> entities);

        @Override
        List<PersonEmailAdress> toEntityList(List<PersonEmailAddressDto> dtos);

        //TODO; CHECK if thats the correct Way!!!
        @Named("personId")
        default Long formatAddress(@NotNull PersonEmailAdress personEmailAddress){
                if(personEmailAddress!=null && personEmailAddress.getPerson()!=null)
                        return personEmailAddress.getPerson().getId();
                throw new IllegalArgumentException();  
        }

        //TODO: also From DTO to ENTITY
        


}