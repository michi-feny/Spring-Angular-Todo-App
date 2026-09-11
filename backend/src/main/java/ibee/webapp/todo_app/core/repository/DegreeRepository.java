package ibee.webapp.todo_app.core.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.hardSkills.Degree;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

@Repository
public interface DegreeRepository 
    extends MyFacadeBaseCrudRepository<Degree, Long>{

    Optional<Degree> findByNameAndWeight(String name, Integer weight);
}
