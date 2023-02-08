package net.chatfilter.chatfilterbackend.persistence.repository;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrganizationRepository extends MongoRepository<Organization, UUID> {
}
