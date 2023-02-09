package net.chatfilter.chatfilterbackend.persistence.service.user;

import net.chatfilter.chatfilterbackend.persistence.entity.user.User;
import net.chatfilter.chatfilterbackend.persistence.repository.UserRepository;
import net.chatfilter.chatfilterbackend.web.security.user.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserAuthUtil userAuthUtil;

    public User create(User user) {
        return repository.insert(user);
    }

    public User getByUUID(UUID uuid) {
        return repository.findById(uuid).orElse(null);
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public UserAuthResult auth(String email, String rawPassword) {
        User user = getByEmail(email);
        if (user == null) {
            return UserAuthResult.UNKNOWN_USER;
        }

        if (userAuthUtil.passwordMatches(rawPassword, user.getEncodedPassword())) {
            return UserAuthResult.SUCCESS;
        }

        return UserAuthResult.WRONG_PASSWORD;
    }
}
