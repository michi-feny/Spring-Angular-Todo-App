package ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonProfessionQualificationId
        implements Serializable {

    @Positive
    @NotNull
    @Column(name = "person_id")
    private Long personId;

    @Positive
    @NotNull
    @Column(name = "profession_qualification_id")
    private Long professionQualificationId;

    @Positive
    @NotNull
    @Column(name = "education_institution_id")
    private Long educationInstitutionId;
}
