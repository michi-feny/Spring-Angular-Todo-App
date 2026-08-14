package ibee.webapp.todo_app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CountryDto(
    

    Long id,

    @NotBlank(message = "{country.code.required}")
    @Size(max = 2, message = "{country.code.maxSize}")
    @Pattern(
        regexp = "^[A-Za-z]{2}$",
        message = "{country.code.invalid}"
    )
    String code, // ISO 3166-1 Alpha-2 (AT, DE, CH, US, ..

    @NotBlank(message = "{country.name.required}")
    @Size(max = 100, message = "{country.name.maxSize}")
    String name,

    @Size(max = 5, message = "{country.language.maxSize}")
    @Pattern(
        regexp = "^[A-Za-z]{2,3}(-[A-Za-z]{2})?$",
        message = "{country.language.invalid}"
    )
    String language
) {

}
