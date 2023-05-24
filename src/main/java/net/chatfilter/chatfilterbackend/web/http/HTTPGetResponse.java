package net.chatfilter.chatfilterbackend.web.http;

public interface HTTPGetResponse {

    void onResponse(String response);

    void onError();
}
