package com.example.myapplication.http.Basenet;

import com.lzy.okgo.cache.CacheMode;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class BaseRequest<R extends BaseRequest> {

    protected String url;
    protected String method;
    protected String baseUrl;
    protected Object tag;
    //    protected CacheMode cacheMode;
    protected Map<String, String> headers;
    protected Map<String, String> urlParams;
    private Request mRequest;

    public BaseRequest(String url) {
        this.url = url;
        baseUrl = url;
    }

    public R url(String url) {
        this.url = url;
        return (R) this;
    }

    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    public R cacheMode(CacheMode cacheMode) {
//        this.cacheMode = cacheMode;
        return (R) this;
    }

    public R headers(Map<String, String> headers) {
        this.headers = headers;
        return (R) this;
    }

    public R addHeader(String key, String value) {
        if (headers == null) headers = new LinkedHashMap<>();
        headers.put(key, value);
        return (R) this;
    }

    public R urlParams(Map<String, String> urlParams) {
        this.urlParams = urlParams;
        return (R) this;
    }

    public R addUrlParams(String key, String value) {
        if (urlParams == null) urlParams = new LinkedHashMap<>();
        urlParams.put(key, value);
        return (R) this;
    }

    public abstract RequestBody generateRequestBody();

    public abstract Request generateRequest(RequestBody requestBody);


    public okhttp3.Call generateCall(Request request) {
        return QDNet.ins().getClient().newCall(request);
    }

    public okhttp3.Call getCall() {
        //构建请求体，返回call对象
        RequestBody requestBody = generateRequestBody();
        mRequest = generateRequest(wrapRequestBody(requestBody));
        return generateCall(mRequest);
    }

    private RequestBody wrapRequestBody(RequestBody requestBody) {
        return requestBody;
    }

    public Response execute() throws IOException {
        return getCall().execute();
    }
}

