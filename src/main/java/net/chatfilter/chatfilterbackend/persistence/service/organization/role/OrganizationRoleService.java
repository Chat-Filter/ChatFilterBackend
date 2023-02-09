package net.chatfilter.chatfilterbackend.persistence.service.organization.role;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import net.chatfilter.chatfilterbackend.persistence.repository.OrganizationRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrganizationRoleService {

    @Autowired
    private OrganizationRoleRepository repository;

    public OrganizationRole get(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }
}
