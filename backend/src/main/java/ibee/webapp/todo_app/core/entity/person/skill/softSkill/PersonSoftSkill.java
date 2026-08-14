package ibee.webapp.todo_app.core.entity.person.skill.softSkill;


import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.SoftSkill;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "person_soft_skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSoftSkill 
    implements PersonRelatedEntity{

    @EmbeddedId
    private PersonSoftSkillId id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @MapsId("personId")
    @JoinColumn(
        name = "person_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_soft_skill_person"
        )
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @MapsId("softSkillId")
    @JoinColumn(
        name = "soft_skill_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_soft_skill_skill"
        )
    )
    private SoftSkill softSkill;
}
