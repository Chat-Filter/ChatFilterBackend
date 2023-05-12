package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;

public class JoinAndLeaveRequest {

    private Key key;
    private String organizationId;

    public JoinAndLeaveRequest(Key key, String organizationId) {
        this.key = key;
        this.organizationId = organizationId;
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
}
