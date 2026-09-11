package ibee.webapp.todo_app.features.person.related.contact.dto;

import ibee.webapp.todo_app.dto.CountryDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record PersonCountryDto(

    
    @Valid
    PersonCountryDtoId id,

    @NotNull
    CountryDto country,

    @NotNull
    Boolean mainCountry
) {

}
