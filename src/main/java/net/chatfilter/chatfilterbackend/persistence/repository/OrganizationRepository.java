package net.chatfilter.chatfilterbackend.persistence.repository;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationRepository extends MongoRepository<Organization, String> {

    Organization findByKey(Key key);
}
