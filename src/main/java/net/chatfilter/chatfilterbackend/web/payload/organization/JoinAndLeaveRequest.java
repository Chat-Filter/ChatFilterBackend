package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;

public class JoinAndLeaveRequest {

    private UserKey key;
    private String organizationId;

    public JoinAndLeaveRequest(UserKey key, String organizationId) {
        this.key = key;
        this.organizationId = organizationId;
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
}
