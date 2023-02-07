package net.chatfilter.chatfilterbackend.persistence.entity.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.api.ApiKey;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.OrganizationMember;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.UUID;

public class Organization {
    private final UUID uuid;
    private final UUID owner;
    private HashMap<UUID, OrganizationMember> members;
    private HashMap<LocalTime, Integer> checks;
    private ApiKey key;
    private String planName;

    public Organization(UUID uuid, UUID owner) {
        this.uuid = uuid;
        this.owner = owner;
        members = new HashMap<>();
        checks = new HashMap<>();
        key = new ApiKey(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public HashMap<UUID, OrganizationMember> getMembers() {
        return members;
    }

    public void setMembers(HashMap<UUID, OrganizationMember> members) {
        this.members = members;
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
