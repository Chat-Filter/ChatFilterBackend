package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;

public class CreateRequest {

    private UserKey key;
    private String name;

    public CreateRequest(UserKey key, String name) {
        this.key = key;
        this.name = name;
    }

    public UserKey getKey() {
        return key;
    }

    public void setKey(UserKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
