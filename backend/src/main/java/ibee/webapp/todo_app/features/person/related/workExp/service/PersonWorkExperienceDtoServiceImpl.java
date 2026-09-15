package ibee.webapp.todo_app.features.person.related.workExp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractMappedPersonRelatedDtoService;
import ibee.webapp.todo_app.core.service.person.related.workExp.PersonWorkExperienceServiceImpl;
import ibee.webapp.todo_app.features.person.related.referenceIds.workExp.PersonWorkExperienceDtoId;
import ibee.webapp.todo_app.features.person.related.workExp.dto.MergeWorkExperiencesRequestDto;
import ibee.webapp.todo_app.features.person.related.workExp.dto.PersonWorkExperienceDto;
import ibee.webapp.todo_app.mapper.WorkExperienceMapper;
import ibee.webapp.todo_app.mapper.person.references.workExp.PersonWorkExperienceReferenceMapper;
import ibee.webapp.todo_app.mapper.person.workExp.PersonWorkExperienceMapper;

@Service
@Transactional
public class PersonWorkExperienceDtoServiceImpl 
    extends AbstractMappedPersonRelatedDtoService<
        PersonWorkExperienceDto, 
        PersonWorkExperience, 
        PersonWorkExperienceId,
        PersonWorkExperienceDtoId> 
{

    private final PersonWorkExperienceServiceImpl entityService;
    private final PersonWorkExperienceMapper personWorkExperienceMapper;
    private final WorkExperienceMapper coreWorkExperienceMapper;

    public PersonWorkExperienceDtoServiceImpl(
            PersonWorkExperienceServiceImpl entityService,
            PersonWorkExperienceMapper mapper,
            WorkExperienceMapper workExperienceMapper,
            PersonWorkExperienceReferenceMapper idReferenceMapper) {
        super(entityService, mapper, idReferenceMapper); 
        this.personWorkExperienceMapper = mapper;
        this.coreWorkExperienceMapper = workExperienceMapper;
        this.entityService = entityService;
    }

    /**
     * Custom endpoint extension not covered by the base CrudDtoService.
     * Executes the merge command and returns the newly synced, gap-free sequence list.
     */
    public List<PersonWorkExperienceDto> mergeWorkExperiences(MergeWorkExperiencesRequestDto request) {
        
        // 1. Execute the Entity-level merge command
        // 1. Execute the merge and instantly receive the gap-free, refreshed list
        List<PersonWorkExperience> refreshedList = entityService.mergeWorkExperiences(
            request.personId(),
            request.workExpIdsToMerge(),
            coreWorkExperienceMapper.toEntity(request.newMasterDetails())
        );

        // 2. Map and return
        return personWorkExperienceMapper.toDtoList(refreshedList);
    }

    // 1. Expose the Flat Data for the Base Controller (Inherited standard behavior)
    public List<PersonWorkExperienceDto> findByPersonId(Long personId) {
        List<PersonWorkExperience> entities = entityService.findByPersonId(personId);
        return personWorkExperienceMapper.toDtoList(entities);
    }

    // 2. Expose the Tree Data for the Custom Controller Endpoint
    public List<PersonWorkExperienceDto> findRootExperiencesTree(Long personId) {
        List<PersonWorkExperience> rootEntities = entityService.findRootExperiencesTree(personId);
        // MapStruct automatically uses toDtoList recursively for subExperiences
        return personWorkExperienceMapper.toDtoList(rootEntities);
    }

    
}
