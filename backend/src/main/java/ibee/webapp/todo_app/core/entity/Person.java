package ibee.webapp.todo_app.core.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ibee.webapp.todo_app.core.entity.person.contactData.address.PersonAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.emailAddress.PersonEmailAddress;
import ibee.webapp.todo_app.core.entity.person.contactData.nationality.PersonCountry;
import ibee.webapp.todo_app.core.entity.person.contactData.phoneNumber.PersonPhoneNumber;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.additionlHardSkill.PersonAdditionalHardSkill;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.degree.PersonDegree;
import ibee.webapp.todo_app.core.entity.person.skill.hardSkill.professionQualification.PersonProfessionQualification;
import ibee.webapp.todo_app.core.entity.person.skill.softSkill.PersonSoftSkill;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.repository.baseRepo.IdentifiableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "person"
)
public class Person implements IdentifiableEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@ValidId
    private Long id;

    @Column(name = "social_record_number", nullable = true, length = 10)
    private Integer socialRecordNumber;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    //@Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, orphanRemoval = true
        //cascade = CascadeType.ALL,
    )
    @Builder.Default
    private Set<PersonCountry> nationalitys = new HashSet<>();

    @OneToMany(
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonAddress> addresses = new HashSet<>();

    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        //cascade = CascadeType.ALL,
        orphanRemoval = true)
    @Builder.Default
    private Set<PersonPhoneNumber> phones = new HashSet<>();

    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        //cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonEmailAddress> emails = new HashSet<>();

    
     @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        //cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonDegree> degrees = new HashSet<>();

     @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        //cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonProfessionQualification> professions = new HashSet<>();


    @OneToMany(
        mappedBy = "person",
        fetch = FetchType.LAZY,
        //cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonAdditionalHardSkill> additionalSkills = new HashSet<>();

    @OneToMany(
    mappedBy = "person",
    fetch = FetchType.LAZY,
    //cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonSoftSkill> softSkills = new HashSet<>();

    @OneToMany(
    mappedBy = "person",
    fetch = FetchType.LAZY,
    //cascade = CascadeType.ALL,
    orphanRemoval = true
    )
    @Builder.Default
    private Set<PersonWorkExperience> workExps = new HashSet<>();

}
