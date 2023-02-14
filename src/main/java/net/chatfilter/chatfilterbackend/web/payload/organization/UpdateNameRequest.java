package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;

public class UpdateNameRequest {

    private UserKey key;
    private String organizationId, name;

    public UpdateNameRequest(UserKey key, String organizationId, String name) {
        this.key = key;
        this.organizationId = organizationId;
        this.name = name;
    }

    public UserKey getKey() {
        return key;
    }

    public void setKey(UserKey key) {
        this.key = key;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
