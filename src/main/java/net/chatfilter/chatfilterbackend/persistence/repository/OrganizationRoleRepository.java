package net.chatfilter.chatfilterbackend.persistence.repository;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationRoleRepository extends MongoRepository<OrganizationRole, String> {

    OrganizationRole getByName(String name);
}
