package net.chatfilter.chatfilterbackend.persistence.entity.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private String email;
    private String name;
    private String lastName;
    private List<UUID> organizations;

    public User(UUID uuid, String email, String name, String lastName) {
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        organizations = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
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
}
