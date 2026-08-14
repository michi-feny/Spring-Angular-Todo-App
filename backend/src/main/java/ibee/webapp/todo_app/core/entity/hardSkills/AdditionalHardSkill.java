package ibee.webapp.todo_app.core.entity.hardSkills;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "additional_hard_skill")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@PrimaryKeyJoinColumn(name = "hard_skill_id")
@DiscriminatorValue("ADDITIONAL")
public class AdditionalHardSkill extends HardSkill {


    /**
     * LANGUAGE
     * IT
     * DRIVING
     */
    @Column(length = 50) //LATER CHANGE; TO CONCRETE CATEGORY ENTITY CLASS
    private String category; //nice to have, aber gibt keinen zusatznutzen, sofern nicht irgendwann mal ein FIlter mit genauen Kategorien benötigt wird


  /*   @ManyToOne
    @JoinColumn(name = "skill_path_id")
    private SkillPath skillPath;
   */
}
