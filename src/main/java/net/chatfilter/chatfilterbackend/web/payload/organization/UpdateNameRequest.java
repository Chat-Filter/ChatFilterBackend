package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;

public class UpdateNameRequest {

    private String key;
    private String organizationId, name;

    public UpdateNameRequest(String key, String organizationId, String name) {
        this.key = key;
        this.organizationId = organizationId;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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
