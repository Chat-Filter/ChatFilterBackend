package net.chatfilter.chatfilterbackend.persistence.service.organization.role;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import net.chatfilter.chatfilterbackend.persistence.repository.OrganizationRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationRoleService {

    @Autowired
    private OrganizationRoleRepository repository;

    public OrganizationRole create(OrganizationRole role) {
        return repository.insert(role);
    }

    public OrganizationRole getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public OrganizationRole getByName(String name) {
        return repository.getByName(name);
    }
}
