package ibee.webapp.todo_app.dto.skills.hard.soft;

import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.security.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SoftSkillDto(
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,

    @NotBlank
    @Size(max = 500)
    String name,   

    @Size(max = 1000)
    String description
) {}
