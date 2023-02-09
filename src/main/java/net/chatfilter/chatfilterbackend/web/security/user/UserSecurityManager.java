package net.chatfilter.chatfilterbackend.web.security.user;

import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class UserSecurityManager {

    private HashMap<UUID, UserKey> users;

    public UserSecurityManager() {
        users = new HashMap<>();
    }

    public void insertUser(UserKey key) {
        users.put(key.getBaseUUID(), key);
    }

    public void deleteUser(UUID uuid) {
        users.remove(uuid);
    }

    public boolean isValid(UserKey key) {
        return users.get(key.getBaseUUID()) != null && users.get(key.getBaseUUID()).equals(key);
    }
}
