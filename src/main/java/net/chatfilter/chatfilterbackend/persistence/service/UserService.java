package net.chatfilter.chatfilterbackend.persistence.service;

import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void create(User user) {
        repository.insert(user);
    }

    public User getByUUID(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void update(User user) {
        repository.save(user);
    }
}
