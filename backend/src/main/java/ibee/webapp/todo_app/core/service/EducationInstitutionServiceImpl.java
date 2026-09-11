package ibee.webapp.todo_app.core.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ibee.webapp.todo_app.core.entity.EducationInstitution;
import ibee.webapp.todo_app.core.repository.EducationInstitutionRepository;
import ibee.webapp.todo_app.core.service.baseService.persist.MyCrudBaseEntityFacedeServiceImpl;
import ibee.webapp.todo_app.mapper.EducationInstitutionMapper;

@Service
@Transactional 
public class EducationInstitutionServiceImpl extends MyCrudBaseEntityFacedeServiceImpl<EducationInstitution, Long> {

    private final EducationInstitutionRepository repository;
    private final AddressServiceImpl addressService;

    public EducationInstitutionServiceImpl(
            EducationInstitutionRepository repository, 
            EducationInstitutionMapper mapper,
            AddressServiceImpl addressService) {
        super(repository, mapper);
        this.repository = repository;
        this.addressService = addressService;
    }

    @Override
    public EducationInstitution create(EducationInstitution requested) {
        // 1. Cascade deduplication down to the Address!
        Assert.notNull(requested, "EdicationInstitute is not allowed to be null");

        if (requested.getAddress() != null) {
            requested.setAddress(addressService.create(requested.getAddress()));
        }

        Long addressId = requested.getAddress() != null ? requested.getAddress().getId() : null;
        Optional<EducationInstitution> existing = repository.findByNameAndAddressId(requested.getName(), addressId);
        
        if (existing.isPresent()) return existing.get();
        return super.create(requested);
    }

    @Override
    public EducationInstitution update(EducationInstitution updates, Long id) {
        Assert.notNull(updates, "EdicationInstitute is not allowed to be null");
        Assert.notNull(id, "the id of EdicationInstitute is not allowed to be null");
        
        if (updates.getAddress() != null) {
            // Update or create the address safely
            if (updates.getAddress().getId() != null) {
                updates.setAddress(addressService.update(updates.getAddress(), updates.getAddress().getId()));
            } else {
                updates.setAddress(addressService.create(updates.getAddress()));
            }
        }

        Long addressId = updates.getAddress() != null ? updates.getAddress().getId() : null;
        Optional<EducationInstitution> existingMatch = repository.findByNameAndAddressId(updates.getName(), addressId);

        if (existingMatch.isPresent() && !existingMatch.get().getId().equals(id)) {
            return existingMatch.get();
        }
        return super.update(updates, id);
    }
}
