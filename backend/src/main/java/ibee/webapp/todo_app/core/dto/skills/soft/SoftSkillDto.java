package ibee.webapp.todo_app.core.dto.skills.soft;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SoftSkillDto(
    Long id,
    @NotBlank
    @Size(max = 500)
    String name,   
    @Size(max = 1000)
    String description
) {}
