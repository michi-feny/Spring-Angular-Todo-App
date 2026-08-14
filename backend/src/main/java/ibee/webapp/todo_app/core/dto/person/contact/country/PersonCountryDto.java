package ibee.webapp.todo_app.core.dto.person.contact.country;

import ibee.webapp.todo_app.core.dto.CountryDto;
import ibee.webapp.todo_app.core.dto.person.referenceIds.contact.PersonCountryDtoId;
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
