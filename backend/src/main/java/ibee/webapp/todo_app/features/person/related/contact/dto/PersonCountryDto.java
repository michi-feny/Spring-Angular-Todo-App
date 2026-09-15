package ibee.webapp.todo_app.features.person.related.contact.dto;

import ibee.webapp.todo_app.dto.CountryDto;
import ibee.webapp.todo_app.features.person.related.referenceIds.contact.PersonCountryDtoId;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record PersonCountryDto(

    
    @NotNull
    @Valid
    PersonCountryDtoId id,

    @NotNull
    Boolean mainCountry
) {

}
