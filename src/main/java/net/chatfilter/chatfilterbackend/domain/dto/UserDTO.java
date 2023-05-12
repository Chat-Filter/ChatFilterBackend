package net.chatfilter.chatfilterbackend.domain.dto;

import net.chatfilter.chatfilterbackend.persistence.entity.user.invite.PendingInvite;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserDTO {

    private String id;
    private String email;
    private String name;
    private String lastName;
    private List<String> organizations;
    private HashMap<String, PendingInvite> pendingOrganizationInvites;

    public UserDTO(String id, String email, String name, String lastName, List<String> organizations, HashMap<String, PendingInvite> pendingOrganizationInvites) {
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

    public List<String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<String> organizations) {
        this.organizations = organizations;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, PendingInvite> getPendingOrganizationInvites() {
        return pendingOrganizationInvites;
    }

    public void setPendingOrganizationInvites(HashMap<String, PendingInvite> pendingOrganizationInvites) {
        this.pendingOrganizationInvites = pendingOrganizationInvites;
    }
}
