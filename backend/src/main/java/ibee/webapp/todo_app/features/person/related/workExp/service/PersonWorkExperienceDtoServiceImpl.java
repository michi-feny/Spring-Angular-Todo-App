package ibee.webapp.todo_app.features.person.related.workExp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperience;
import ibee.webapp.todo_app.core.entity.person.workExperience.PersonWorkExperienceId;
import ibee.webapp.todo_app.core.service.baseService.transport.AbstractMappedCrudDtoService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.AbstractPersonRelatedDtoQuerryAndDeleteServiceService;
import ibee.webapp.todo_app.core.service.person.related.baseInfrastructure.PersonRelatedService;
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
    extends AbstractMappedCrudDtoService<
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

    
}
