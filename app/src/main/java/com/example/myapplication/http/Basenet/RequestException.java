package com.example.myapplication.http.Basenet;

public class RequestException extends Exception {

    public RequestException(int code, String message) {
        super("code=" + code + "," + message);
    }

}
