package com.example.myapplication.http.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HttpCache extends RealmObject {
    @PrimaryKey()
    private String url;
    private String response;
    private byte[] responseByte;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public byte[] getResponseByte() {
        return responseByte;
    }

    public void setResponseByte(byte[] responseByte) {
        this.responseByte = responseByte;
    }
}
