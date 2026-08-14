package ibee.webapp.todo_app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddressDto(

    @NotNull
    @Positive
    Long id,

    @NotBlank
    @Size( max = 200)
    String street,

    @NotBlank
    @Size(max = 20)
    String houseNumber,

    @NotBlank
    @Size( max = 20)
    String zipCode,

    @NotBlank
    @Size(max =100)
    String city,

    @NotNull
    CountryDto country

) {
}
