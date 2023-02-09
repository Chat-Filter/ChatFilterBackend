package net.chatfilter.chatfilterbackend.persistence.entity.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.key.ApiKey;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.OrganizationMember;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Organization {
    private final UUID uuid;
    private final UUID owner;
    private String name;
    private HashMap<UUID, OrganizationMember> members;
    private List<UUID> pendingInvites;
    private HashMap<LocalTime, Integer> checks;
    private ApiKey key;
    private String planName;

    public Organization(UUID uuid, UUID owner, String name) {
        this.uuid = uuid;
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
        this.pendingInvites = new ArrayList<>();
        this.checks = new HashMap<>();
        this.key = new ApiKey(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<UUID, OrganizationMember> getMembers() {
        return members;
    }

    public void setMembers(HashMap<UUID, OrganizationMember> members) {
        this.members = members;
    }

    public List<UUID> getPendingInvites() {
        return pendingInvites;
    }

    public void setPendingInvites(List<UUID> pendingInvites) {
        this.pendingInvites = pendingInvites;
    }

    public HashMap<LocalTime, Integer> getChecks() {
        return checks;
    }

    public void setChecks(HashMap<LocalTime, Integer> checks) {
        this.checks = checks;
    }

    public ApiKey getKey() {
        return key;
    }

    public void setKey(ApiKey key) {
        this.key = key;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
