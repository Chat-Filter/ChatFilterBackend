package net.chatfilter.chatfilterbackend.persistence.entity.organization.role;

import java.util.List;

public class OrganizationRole {
    private final String name;
    private List<OrganizationPermission> permissions;

    public OrganizationRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<OrganizationPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<OrganizationPermission> permissions) {
        this.permissions = permissions;
    }
}
