package ibee.webapp.todo_app.core.entity.hardSkills;




import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profession_qualification")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "hard_skill_id")
@DiscriminatorValue("PROFESSION")
public class ProfessionQualification extends HardSkill {


    /**
     * Geselle
     * Meister
     * Techniker
     */
    /* 
    private String professionType; //ist implizit bereits im level unter HardSkill anzugeben
*/
    @Column(nullable = false)
    private Integer weight;

/* 
    private String field;//Das Berufsfeld allgemein: Elektronik, Bauwesen,...
*/

   
}
