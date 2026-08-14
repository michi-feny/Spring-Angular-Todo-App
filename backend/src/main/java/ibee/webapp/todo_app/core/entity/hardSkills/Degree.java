package ibee.webapp.todo_app.core.entity.hardSkills;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "degree")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "hard_skill_id")
@DiscriminatorValue("DEGREE")
public class Degree extends HardSkill {


    /**
     * Akademisch:
     * Bachelor
     * Master
     * Doctor
     */
   // private String degreeType; //nicht benötigte information, da das attribut name von HardSkills den Academischen Namen bereits Modelliert


    /**
     * Rangordnung:
     *
     * Bachelor 30
     * Master   50
     * Doctor   70
     */
    @Column(nullable = false)
    private Integer weight;

    @Column(name = "pre_name", nullable = false)
    private boolean preName;

    @Column(name = "post_name", nullable = false)
    private boolean postName;

    


}
