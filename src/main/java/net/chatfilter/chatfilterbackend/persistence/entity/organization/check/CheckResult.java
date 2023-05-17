package net.chatfilter.chatfilterbackend.persistence.entity.organization.check;

public class CheckResult {

    private String text;
    private int result;

    public CheckResult(String text, int result) {
        this.text = text;
        this.result = result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
