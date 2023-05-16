package net.chatfilter.chatfilterbackend.persistence.entity.organization;

import net.chatfilter.chatfilterbackend.persistence.entity.Key;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.invite.InvitedUser;
import net.chatfilter.chatfilterbackend.persistence.entity.organization.member.OrganizationMember;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Document(collection = "organizations")
public class Organization {

    @Id
    protected String id;

    private final String owner;
    private String name;
    private HashMap<String, OrganizationMember> members;
    private HashMap<String, InvitedUser> pendingInvites;
    private HashMap<LocalDate, Integer> checks;
    private Key key;
    private String planName;

    public Organization(String owner, String name) {
        this.owner = owner;
        this.name = name;
        this.members = new HashMap<>();
        this.pendingInvites = new HashMap<>();
        this.checks = new HashMap<>();
        this.planName = "Beta";
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

    public HashMap<String, InvitedUser> getPendingInvites() {
        return pendingInvites;
    }

    public void setPendingInvites(HashMap<String, InvitedUser> pendingInvites) {
        this.pendingInvites = pendingInvites;
    }

    public HashMap<LocalDate, Integer> getChecks() {
        return checks;
    }

    public void setChecks(HashMap<LocalDate, Integer> checks) {
        this.checks = checks;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
