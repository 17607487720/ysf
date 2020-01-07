package com.example.myapplication.http.Basenet;

import com.example.myapplication.model.BaseResponse;
import com.google.gson.Gson;

import okhttp3.Response;
import rx.Observable;

public class BaseCenter {
    public static <T> Observable<T> request(BaseRequest request, Class<T> tClass) {
        return RxNetOnSubscribe.create(request.getCall(), tClass).toRxMain().map(response -> response.getData());
    }

    public static <T> Observable<BaseResponse<T>> cacheRequest(BaseRequest request, Class<T> tClass) {
        return RxNetOnSubscribe.create(request.getCall(), tClass).enableCache(true).toRxMain();
    }

    public static <T> Observable<BaseResponse<T>> cacheRequest(BaseRequest request, Class<T> tClass, boolean keepCache, boolean takeCache) {
        return RxNetOnSubscribe.create(request.getCall(), tClass).keepCache(keepCache).takeCache(takeCache).toRxMain();
    }

    public static <T> T blockRequest(BaseRequest request, Class<T> tClass) {
        T result = null;
        try {
            Response response = request.getCall().execute();
            String json = response.body().string();
            result = new Gson().fromJson(json, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

