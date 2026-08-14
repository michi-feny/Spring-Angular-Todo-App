package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.hardSkills.Degree;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface DegreeRepository 
    extends MyBaseCrudRepo<Degree, Long>{
}
