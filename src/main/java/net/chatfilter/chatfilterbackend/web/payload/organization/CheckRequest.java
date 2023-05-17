package net.chatfilter.chatfilterbackend.web.payload.organization;

public class CheckRequest {

    private String apiKey;
    private String text;

    public CheckRequest(String apiKey, String text) {
        this.apiKey = apiKey;
        this.text = text;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
