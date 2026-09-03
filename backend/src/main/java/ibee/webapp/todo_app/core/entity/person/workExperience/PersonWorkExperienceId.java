package ibee.webapp.todo_app.core.entity.person.workExperience;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class represents the Composite Primary Key for PersonWorkExperience.
 * It must implement Serializable and have equals() / hashCode() (provided by @Data).
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonWorkExperienceId implements Serializable {

    @NotNull
    @Positive
    @Column(name = "person_id")
    private Long personId;

    @NotNull
    @Positive
    @Column(name = "work_experience_id")
    private Long workExperienceId;
}
