package net.chatfilter.chatfilterbackend.web.http;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HTTPGet {
    private final String url;
    private final HTTPGetResponse response;
    private final OkHttpClient client;

    public HTTPGet(String url, HTTPGetResponse response) {
        this.url = url;
        this.response = response;
        this.client = new OkHttpClient();
    }

    public void execute() {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                response.onError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                HTTPGet.this.response.onResponse(response.body().string());
            }
        });
    }
}
