package net.chatfilter.chatfilterbackend.persistence.service.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import net.chatfilter.chatfilterbackend.persistence.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    public Organization create(Organization organization) {
        return repository.insert(organization);
    }

    public Organization getByUUID(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }

    public Organization update(Organization organization) {
        return repository.save(organization);
    }

    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }
}
