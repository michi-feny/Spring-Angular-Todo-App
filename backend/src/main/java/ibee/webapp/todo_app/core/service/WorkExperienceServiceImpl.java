package ibee.webapp.todo_app.core.service;

import ibee.webapp.todo_app.core.entity.Company;
import ibee.webapp.todo_app.core.entity.WorkExperience;
import ibee.webapp.todo_app.core.repository.person.WorkExperienceRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.features.person.related.workExp.dto.WorkExperienceDto;
import ibee.webapp.todo_app.mapper.WorkExperienceMapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class WorkExperienceServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<WorkExperience, Long> {


    
    private final CompanyServiceImpl companyService;

    public WorkExperienceServiceImpl(
            WorkExperienceRepository repository,
            WorkExperienceMapper entityMapper,
            CompanyServiceImpl companyService) {
        super(repository, entityMapper);
        this.companyService = companyService;
    }

   @Override
    public WorkExperience create(WorkExperience entity) {
        Assert.notNull(entity, "WorkExperience entity cannot be null");

        Optional<WorkExperience> existingById = Optional.ofNullable(entity.getId())
                .flatMap(this::findWithDetailsById);
        if (existingById.isPresent()) {
            return existingById.get();
        }

        entity.setCompany(lookupToFetchExistingCompanyOrCreateNewCompany(entity.getCompany()));

        return super.create(entity);
    }

   @Override
    public WorkExperience update(WorkExperience incomingUpdates, Long id) {
        Assert.notNull(incomingUpdates, "WorkExperience update payload cannot be null");
        Assert.notNull(id, "WorkExperience ID cannot be null");

        Optional.ofNullable(incomingUpdates.getCompany())
                .ifPresent(company -> incomingUpdates.setCompany(lookupToFetchExistingCompanyOrCreateNewCompany(company)));

        return super.update(incomingUpdates, id);
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id, "WorkExperience ID cannot be null");
        try {
            super.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete this work experience because it is still referenced by person records.");
        }
    }

    @Override
    public void delete(WorkExperience entity) {
        Assert.notNull(entity, "WorkExperience entity cannot be null");
        try {
            super.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete this work experience because it is still referenced by person records.");
        }
    }

    
    public Optional<WorkExperience> findWithDetailsById(Long id){
        return findByIdWithoutException(id);
    }

    /**
     * Flattens the lookup-or-create resolution logic into clean guard clauses, 
     * completely removing nested if-else structures.
     */
    private Company lookupToFetchExistingCompanyOrCreateNewCompany(Company company) {
        if (company == null) {
            return null;
        }
        
        // Delegates entirely to CompanyServiceImpl, which handles ID lookup, 
        // address resolution, and Name + Country deduplication automatically.
        return companyService.create(company);
    }

    /**
     * Exposes a safe cleanup hook for the Orchestration layer to call,
     * without leaking the CompanyService dependency upwards.
     */
    public void cleanupOrphanedCompany(Long companyId) {
        Assert.notNull(companyId,"the id of the Company is not allowed to be null");
        
        companyService.deleteIfOrphaned(companyId);
        
    }

    
    
}
