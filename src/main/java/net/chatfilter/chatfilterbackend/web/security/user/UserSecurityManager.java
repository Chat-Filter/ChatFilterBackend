package net.chatfilter.chatfilterbackend.web.security.user;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UserSecurityManager {

    private HashMap<String, Key> users;

    public UserSecurityManager() {
        users = new HashMap<>();
    }

    public void insertUser(Key key) {
        users.put(key.getBaseId(), key);
    }

    public void deleteUser(String id) {
        users.remove(id);
    }

    public boolean isValid(Key key) {
        return users.get(key.getBaseId()) != null && users.get(key.getBaseId()).equals(key);
    }
}
