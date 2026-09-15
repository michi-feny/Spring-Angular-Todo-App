package ibee.webapp.todo_app.dto.skills.hard;

import ibee.webapp.todo_app.dto.skills.hard.SkillType;
import ibee.webapp.todo_app.validation.idHandle.create.OnCreate;
import ibee.webapp.todo_app.validation.idHandle.create.ValidCreateId;
import ibee.webapp.todo_app.validation.idHandle.update.OnUpdate;
import ibee.webapp.todo_app.validation.idHandle.update.ValidUpdateId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record ProfessionQualificationDto(
    @ValidCreateId(groups = OnCreate.class)
    @ValidUpdateId(groups = OnUpdate.class)
    Long id,
    
    @NotBlank
    @Size(max = 500)
    String name,// eg Meister, Techniker, Geselle
    @Size(max = 100)
    String level,
    SkillType skillType,            // SkillType.PROFESSION_QUALIFICATION
    Integer weight //eg Meister hat 30 -genau so wie der bachelor of science
) {}
