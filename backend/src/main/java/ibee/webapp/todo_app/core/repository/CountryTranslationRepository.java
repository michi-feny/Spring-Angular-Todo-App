package ibee.webapp.todo_app.core.repository;

import org.springframework.stereotype.Repository;

import ibee.webapp.todo_app.core.entity.CountryTranslation;
import ibee.webapp.todo_app.core.repository.baseRepo.MyBaseCrudRepo;

@Repository
public interface CountryTranslationRepository 
    extends MyBaseCrudRepo<CountryTranslation, Long>{

}
