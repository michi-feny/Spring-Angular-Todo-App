package ibee.webapp.todo_app.core.dto.person.referenceIds.contact;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonCountryDtoId(
    @NotNull
    @Positive
    Long countryId,

    @NotNull
    @Positive
    Long personId
) {

}
