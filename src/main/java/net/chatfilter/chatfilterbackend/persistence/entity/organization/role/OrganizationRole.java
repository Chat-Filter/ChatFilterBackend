package net.chatfilter.chatfilterbackend.persistence.entity.organization.role;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "organization_roles")
public class OrganizationRole {

    @Id
    private String id;
    private String name;
    private List<OrganizationPermission> permissions;

    public OrganizationRole(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
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
