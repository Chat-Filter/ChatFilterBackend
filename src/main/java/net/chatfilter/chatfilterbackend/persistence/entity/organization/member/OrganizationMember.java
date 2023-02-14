package net.chatfilter.chatfilterbackend.persistence.entity.organization.member;

import java.util.UUID;

public class OrganizationMember {
    private final String id;
    private String roleId;

    public OrganizationMember(String id, String roleId) {
        this.id = id;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return roleId;
    }

    public void setRole(String roleId) {
        this.roleId = roleId;
    }
}
