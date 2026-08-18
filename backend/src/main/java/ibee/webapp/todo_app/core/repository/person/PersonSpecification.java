package ibee.webapp.todo_app.core.repository.person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import ibee.webapp.todo_app.core.entity.Person;
import jakarta.persistence.criteria.Predicate;

public class PersonSpecification {

    public static Specification<Person> filterBy(
            String firstName, 
            String lastName, 
            java.time.LocalDate birthDate, 
            Short socialRecordNumber) {
        
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null && !firstName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }
            if (lastName != null && !lastName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }
            if (birthDate != null) {
                predicates.add(cb.equal(root.get("birthDate"), birthDate));
            }
            if (socialRecordNumber != null && socialRecordNumber != 0) {
                predicates.add(cb.equal(root.get("socialRecordNumber"), socialRecordNumber));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
