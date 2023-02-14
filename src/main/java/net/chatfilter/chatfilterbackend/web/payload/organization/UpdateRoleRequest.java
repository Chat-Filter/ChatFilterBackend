package net.chatfilter.chatfilterbackend.web.payload.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.user.key.UserKey;

public class UpdateRoleRequest {

    private UserKey key;
    private String userToUpdate;
    private String organizationId;
    private String role;

    public UpdateRoleRequest(UserKey key, String userToUpdate, String organizationId, String role) {
        this.key = key;
        this.userToUpdate = userToUpdate;
        this.organizationId = organizationId;
        this.role = role;
    }

    public UserKey getKey() {
        return key;
    }

    public void setKey(UserKey key) {
        this.key = key;
    }

    public String getUserToUpdate() {
        return userToUpdate;
    }

    public void setUserToUpdate(String userToUpdate) {
        this.userToUpdate = userToUpdate;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
