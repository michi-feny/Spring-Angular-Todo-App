package ibee.webapp.todo_app.core.dto.address;

import ibee.webapp.todo_app.core.dto.CountryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAddressDto(

    

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
