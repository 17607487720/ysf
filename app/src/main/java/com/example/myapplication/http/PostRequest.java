package com.example.myapplication.http;

import com.example.myapplication.utils.HttpUtils;

import okhttp3.Request;
import okhttp3.RequestBody;

public class PostRequest extends BaseBodyRequest<PostRequest> {

    public PostRequest(String url) {
        super(url);
        method = "POST";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        return new Request.Builder()
                .post(requestBody)
                .headers(HttpUtils.createHeaders(headers))
                .url(HttpUtils.createUrlFromParams(baseUrl, urlParams))
                .tag(tag)
                .build();
    }
}
