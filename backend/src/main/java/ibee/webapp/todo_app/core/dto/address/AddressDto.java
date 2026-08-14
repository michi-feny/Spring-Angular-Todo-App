package ibee.webapp.todo_app.core.dto.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddressDto(

    @NotNull
    @Positive
    Long id,

    @NotNull
    CreateAddressDto createdAddressDto
) {

}
