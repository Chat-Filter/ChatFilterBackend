package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;

public class JoinAndLeaveRequest {

    private String key;
    private String organizationId;

    public JoinAndLeaveRequest(String key, String organizationId) {
        this.key = key;
        this.organizationId = organizationId;
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
}
