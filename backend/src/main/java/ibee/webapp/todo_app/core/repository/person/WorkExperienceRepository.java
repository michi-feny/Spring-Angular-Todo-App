package ibee.webapp.todo_app.core.repository.person;

import ibee.webapp.todo_app.core.entity.WorkExperience;
import ibee.webapp.todo_app.core.repository.baseRepo.MyFacadeBaseCrudRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository 
extends MyFacadeBaseCrudRepository<WorkExperience, Long> {

    @Modifying
    @Query("DELETE FROM WorkExperience w WHERE NOT EXISTS (SELECT pwe FROM PersonWorkExperience pwe WHERE pwe.workExperience = w)")
    int deleteUnlinkedWorkExperiences();
}


