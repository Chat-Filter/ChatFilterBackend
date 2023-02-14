package net.chatfilter.chatfilterbackend.persistence.entity.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.organization.key.ApiKey;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.OrganizationMember;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Document(collection = "organizations")
public class Organization {

    @Id
    private String id;

    @Id
    private final String owner;
    private String name;
    private HashMap<String, OrganizationMember> members;
    private List<String> pendingInvites;
    private HashMap<LocalTime, Integer> checks;
    private ApiKey key;
    private String planName;

    public Organization(String owner, String name) {
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
        this.pendingInvites = new ArrayList<>();
        this.checks = new HashMap<>();
        this.key = new ApiKey(id);
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, OrganizationMember> getMembers() {
        return members;
    }

    public void setMembers(HashMap<String, OrganizationMember> members) {
        this.members = members;
    }

    public List<String> getPendingInvites() {
        return pendingInvites;
    }

    public void setPendingInvites(List<String> pendingInvites) {
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
