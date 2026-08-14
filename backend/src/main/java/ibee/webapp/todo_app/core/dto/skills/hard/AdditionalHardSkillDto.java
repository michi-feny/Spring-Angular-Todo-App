package ibee.webapp.todo_app.core.dto.skills.hard;

import ibee.webapp.todo_app.core.dto.skills.hard.SkillType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdditionalHardSkillDto(
    Long id,
    @NotBlank
    @Size(max = 500)
    String name,    //eg Führerschein, Englisch, Java
    @Size(max = 100)
    String level,//eg B, C1, Expert
    SkillType skillType           // SkillType.ADDITIONAL_HARD_SKILL
    
) {}
