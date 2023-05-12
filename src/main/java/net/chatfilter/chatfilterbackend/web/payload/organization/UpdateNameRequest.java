package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;

public class UpdateNameRequest {

    private Key key;
    private String organizationId, name;

    public UpdateNameRequest(Key key, String organizationId, String name) {
        this.key = key;
        this.organizationId = organizationId;
        this.name = name;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
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
