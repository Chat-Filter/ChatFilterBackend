package net.chatfilter.chatfilterbackend.web.security.user;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.key.ApiKey;
import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class UserSecurityManager {

    private HashMap<UserKey, UUID> users;

    public UserSecurityManager() {
        users = new HashMap<>();
    }

    public void addUser(UserKey key, UUID userUUID) {
        users.put(key, userUUID);
    }

    public UUID getUser(UserKey key) {
        return users.get(key);
    }

    public void removeUser(UserKey key) {
        users.remove(key);
    }

    public boolean containsUser(UserKey key) {
        return users.containsKey(key) && users.get(key) != null && users.get(key).equals(key.getBaseUUID());
    }
}
