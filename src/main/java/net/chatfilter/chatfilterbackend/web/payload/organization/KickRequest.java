package net.chatfilter.chatfilterbackend.web.payload.organization;

public class KickRequest {

    private String key;
    private String userIdToKick;
    private String organizationId;

    public KickRequest(String key, String userToKick, String organizationId) {
        this.key = key;
        this.userIdToKick = userToKick;
        this.organizationId = organizationId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserIdToKick() {
        return userIdToKick;
    }

    public void setUserIdToKick(String userIdToKick) {
        this.userIdToKick = userIdToKick;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
