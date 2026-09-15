package ibee.webapp.todo_app.core.entity.person.skill.softSkill;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonSoftSkillId implements Serializable {

    @NotNull
    @Positive   
    @Column(name = "person_id")
    private Long personId;

    
    @Column(name = "soft_skill_id")
    private Long softSkillId;
}
