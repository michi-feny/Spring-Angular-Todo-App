package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill;


import java.io.Serializable;

import jakarta.persistence.*;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonAdditionalHardSkillId
        implements Serializable {

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "additional_hard_skill_id")
    private Long additionalHardSkillId;
}
