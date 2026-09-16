package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree;


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
public class PersonDegreeId implements Serializable {

    @Column(name = "person_id")
    @Positive
    @NotNull
    private Long personId;

    @Column(name = "degree_id")
    private Long degreeId;

    @Column(name = "education_institution_id")
    private Long educationInstitutionId;
}
