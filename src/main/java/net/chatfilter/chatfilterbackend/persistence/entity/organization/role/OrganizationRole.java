package net.chatfilter.chatfilterbackend.persistence.entity.organization.role;

import java.util.List;
import java.util.UUID;

public class OrganizationRole {
    private final UUID uuid;
    private String name;
    private List<OrganizationPermission> permissions;

    public OrganizationRole(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrganizationPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<OrganizationPermission> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(OrganizationPermission permission) {
        return permissions.contains(permission) || permissions.contains(OrganizationPermission.ALL_PERMISSIONS);
    }
}
