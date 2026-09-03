package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill;


import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonAdditionalHardSkillId
        implements Serializable {

    @NotNull
    @Positive
    @Column(name = "person_id")
    private Long personId;

    @NotNull
    @Positive
    @Column(name = "additional_hard_skill_id")
    private Long additionalHardSkillId;
}
