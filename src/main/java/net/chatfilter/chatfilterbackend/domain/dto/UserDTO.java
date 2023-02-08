package net.chatfilter.chatfilterbackend.domain.dto;

import java.util.List;
import java.util.UUID;

public class UserDTO {

    private final UUID uuid;
    private String email;
    private String name;
    private String lastName;
    private List<UUID> organizations;

    public UserDTO(UUID uuid, String email, String name, String lastName, List<UUID> organizations) {
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.organizations = organizations;
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
