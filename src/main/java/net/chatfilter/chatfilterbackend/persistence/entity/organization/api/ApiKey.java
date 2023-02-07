package net.chatfilter.chatfilterbackend.persistence.entity.organization.api;

import java.util.UUID;

public class ApiKey {

    private String key;

    public ApiKey(UUID organizationUUID) {
        this.key = organizationUUID.toString() + "-" + UUID.randomUUID().toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
