package net.chatfilter.chatfilterbackend.persistence.entity.organization.member;

import java.util.UUID;

public class OrganizationMember {
    private final UUID uuid;
    private UUID role;

    public OrganizationMember(UUID uuid, UUID role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getRole() {
        return role;
    }

    public void setRole(UUID role) {
        this.role = role;
    }
}
