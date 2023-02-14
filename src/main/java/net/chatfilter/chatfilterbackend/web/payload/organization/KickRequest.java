package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;

public class KickRequest {

    private UserKey key;
    private String userToKick;
    private String organizationId;

    public KickRequest(UserKey key, String userToKick, String organizationId) {
        this.key = key;
        this.userToKick = userToKick;
        this.organizationId = organizationId;
    }

    public UserKey getKey() {
        return key;
    }

    public void setKey(UserKey key) {
        this.key = key;
    }

    public String getUserToKick() {
        return userToKick;
    }

    public void setUserToKick(String userToKick) {
        this.userToKick = userToKick;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
