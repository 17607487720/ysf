package com.example.myapplication.http;

import com.example.myapplication.utils.HttpUtils;

import okhttp3.Request;
import okhttp3.RequestBody;

public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(String url) {
        super(url);
        method = "GET";
    }

    @Override
    public RequestBody generateRequestBody() {
        return null;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        return new Request.Builder()
                .get()
                .headers(HttpUtils.createHeaders(headers))
                .url(HttpUtils.createUrlFromParams(baseUrl, urlParams))
                .tag(tag)
                .build();
    }
}
