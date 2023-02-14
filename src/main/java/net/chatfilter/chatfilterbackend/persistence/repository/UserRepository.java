package net.chatfilter.chatfilterbackend.persistence.repository;

import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}
