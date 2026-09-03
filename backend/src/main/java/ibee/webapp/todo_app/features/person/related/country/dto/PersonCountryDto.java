package ibee.webapp.todo_app.features.person.related.country.dto;

import ibee.webapp.todo_app.core.dto.CountryDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import jakarta.validation.constraints.NotNull;

public record PersonCountryDto(

    @NotNull
    PersonCountryDtoId id,

    @NotNull
    CountryDto country,

    @NotNull
    Boolean mainCountry
) {

}
