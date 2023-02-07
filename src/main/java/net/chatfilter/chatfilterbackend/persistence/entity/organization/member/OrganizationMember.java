package net.chatfilter.chatfilterbackend.persistence.entity.organization.member;

import java.util.UUID;

public class OrganizationMember {
    private final UUID uuid;
    private String role;

    public OrganizationMember(UUID uuid, String role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
