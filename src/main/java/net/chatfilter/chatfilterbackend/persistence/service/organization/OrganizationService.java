package net.chatfilter.chatfilterbackend.persistence.service.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import net.chatfilter.chatfilterbackend.persistence.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    public Organization create(Organization organization) {
        return repository.insert(organization);
    }

    public Organization getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Organization update(Organization organization) {
        return repository.save(organization);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
