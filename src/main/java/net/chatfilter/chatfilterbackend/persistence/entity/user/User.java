package net.chatfilter.chatfilterbackend.persistence.entity.user;

import net.chatfilter.chatfilterbackend.persistence.entity.user.invite.PendingInvite;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String encodedPassword;
    private String name;
    private String lastName;
    private List<String> organizations;
    private HashMap<String, PendingInvite> pendingOrganizationInvites;

    public User(String email, String encodedPassword, String name, String lastName) {
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.name = name;
        this.lastName = lastName;
        organizations = new ArrayList<>();
        pendingOrganizationInvites = new HashMap<>();
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

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
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

    public HashMap<String, PendingInvite> getPendingOrganizationInvites() {
        return pendingOrganizationInvites;
    }

    public void setPendingOrganizationInvites(HashMap<String, PendingInvite> pendingOrganizationInvites) {
        this.pendingOrganizationInvites = pendingOrganizationInvites;
    }
}
