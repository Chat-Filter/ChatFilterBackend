package net.chatfilter.chatfilterbackend.persistence.repository;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.role.OrganizationRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrganizationRoleRepository extends MongoRepository<OrganizationRole, UUID> {

    OrganizationRole getByName(String name);
}
