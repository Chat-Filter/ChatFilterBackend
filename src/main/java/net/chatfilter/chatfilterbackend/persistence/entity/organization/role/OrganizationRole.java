package net.chatfilter.chatfilterbackend.persistence.entity.organization.role;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document(collection = "organization_roles")
public class OrganizationRole {

    @Id
    private String id;
    private String name;
    private List<OrganizationPermission> permissions;

    public OrganizationRole(String name) {
        this.name = name;
        permissions = new ArrayList<>();
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

    public void addPermission(OrganizationPermission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
    }

    public void removePermission(OrganizationPermission... permission) {
        permissions.removeAll(Arrays.asList(permission));
    }

    public boolean hasPermission(OrganizationPermission permission) {
        return permissions.contains(permission) || permissions.contains(OrganizationPermission.ALL_PERMISSIONS);
    }
}
