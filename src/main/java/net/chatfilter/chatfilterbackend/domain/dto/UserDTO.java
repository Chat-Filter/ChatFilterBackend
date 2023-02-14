package net.chatfilter.chatfilterbackend.domain.dto;

import java.util.List;
import java.util.UUID;

public class UserDTO {

    private String id;
    private String email;
    private String name;
    private String lastName;
    private List<UUID> organizations;
    private List<UUID> pendingOrganizationInvites;

    public UserDTO(String id, String email, String name, String lastName, List<UUID> organizations, List<UUID> pendingOrganizationInvites) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.organizations = organizations;
        this.pendingOrganizationInvites = pendingOrganizationInvites;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UUID> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<UUID> organizations) {
        this.organizations = organizations;
    }

    public List<UUID> getPendingOrganizationInvites() {
        return pendingOrganizationInvites;
    }

    public void setPendingOrganizationInvites(List<UUID> pendingOrganizationInvites) {
        this.pendingOrganizationInvites = pendingOrganizationInvites;
    }
}
