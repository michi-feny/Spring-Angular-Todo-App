package ibee.webapp.todo_app.core.entity.hardSkills;

import org.hibernate.Hibernate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hard_skill")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "skill_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class HardSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
        nullable = false,
        length = 500)
    /**
     * Beispiele: 
     * Master of Sience
     * Führerschein
     * Englisch
     */
    private String name;



    /**
     * Beispiele:
     * MSc
     * B
     * C1
     * Expert
     */
    @Column(length = 100)
    private String level;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        HardSkill other = (HardSkill) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }


}
