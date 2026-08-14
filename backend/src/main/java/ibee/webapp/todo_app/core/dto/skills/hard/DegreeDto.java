package ibee.webapp.todo_app.core.dto.skills.hard;

import ibee.webapp.todo_app.core.dto.skills.hard.SkillType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DegreeDto(

    Long id,

    @NotBlank
    @Size(max = 500)
    String name, // e.g. "Bachelor of Science in Computer Science"  

    @NotBlank
    @Size(max = 100)
    String level,   //MSc BSc,...

    SkillType skillType,            // SkillType.DEGREE

    @NotNull
    @Positive
    Integer weight, // eg 30 for Bachelor, 60 for Master, 90 for PhD

    @NotNull
    Boolean preName,   //is the title before the name (e.g. Dr.) or after the name (e.g. PhD)

    @NotNull
    Boolean postName//is the title after the name (e.g. PhD) or before the name (e.g. Dr.)
    
) {}
