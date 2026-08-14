package ibee.webapp.todo_app.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface EducationInstituteRepository 
    extends MyBaseCrudRepo
        <EducationInstitution, Long>{


    @EntityGraph(attributePaths = {
    "address",
    "address.country"
})
Optional<EducationInstitution> findWithDetailsById(Long id);
}
