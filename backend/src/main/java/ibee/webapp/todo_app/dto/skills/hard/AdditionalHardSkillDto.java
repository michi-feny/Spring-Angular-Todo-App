package ibee.webapp.todo_app.dto.skills.hard;

import ibee.webapp.todo_app.dto.skills.hard.SkillType;
import ibee.webapp.todo_app.security.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.security.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.security.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.security.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record AdditionalHardSkillDto(
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,
    @NotBlank
    @Size(max = 500)
    String name,    //eg Führerschein, Englisch, Java
    @Size(max = 100)
    String level,//eg B, C1, Expert
    SkillType skillType           // SkillType.ADDITIONAL_HARD_SKILL
    
) {}
