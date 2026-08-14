package ibee.webapp.todo_app.core.dto.skills.hard;

import ibee.webapp.todo_app.core.dto.skills.hard.SkillType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessionQualificationDto(
    Long id,
    @NotBlank
    @Size(max = 500)
    String name,// eg Meister, Techniker, Geselle
    @Size(max = 100)
    String level,
    SkillType skillType,            // SkillType.PROFESSION_QUALIFICATION
    Integer weight //eg Meister hat 30 -genau so wie der bachelor of science
) {}
