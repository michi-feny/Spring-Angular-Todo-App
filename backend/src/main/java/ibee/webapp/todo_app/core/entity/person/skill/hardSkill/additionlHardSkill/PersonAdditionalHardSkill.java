package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill;
import ibee.webapp.todo_app.core.entity.Person;
import ibee.webapp.todo_app.core.entity.hardSkills.AdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.PersonRelatedEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * PersonAdditionalSkill
 */
@Entity
@Table(name = "person_additional_skill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonAdditionalHardSkill 
    implements PersonRelatedEntity{

    @EmbeddedId
    private PersonAdditionalHardSkillId id;


    @ManyToOne
    @MapsId("personId")
    @JoinColumn(
        name = "person_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_additional_skill_person"
        )
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;


    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @MapsId("additionalHardSkillId")
    @JoinColumn(
        name = "additional_hard_skill_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_person_additional_skill"
        )
    )
    private AdditionalHardSkill additionalHardSkill;

}
