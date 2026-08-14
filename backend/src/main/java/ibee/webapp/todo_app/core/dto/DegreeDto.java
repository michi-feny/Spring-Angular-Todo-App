package ibee.webapp.todo_app.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DegreeDto(

    Long id,

    @NotBlank
    @Size(max = 500)
    /**
     * Beispiele: 
     * Master of Sience
     */
    String name,

    @NotBlank
    @Size(max = 100)
    /**
     * Beispiele:
     * MSc
     */
    String level,

    @NotNull
    @Positive
    /**
     * Rangordnung:
     *
     * Bachelor 30
     * Master   50
     * Doctor   70
     */
    Integer weight,

    @NotNull
    Boolean preName,

    @NotNull
    Boolean postName
) {

}
