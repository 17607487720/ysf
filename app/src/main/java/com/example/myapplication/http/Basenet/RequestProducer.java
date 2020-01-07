package com.example.myapplication.http.Basenet;

import android.util.Log;

import com.example.myapplication.http.realm.HttpCache;
import com.example.myapplication.model.BaseResponse;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Response;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;

public class RequestProducer<T> extends AtomicBoolean implements Subscription, Producer {
    private final Call call;
    private final Subscriber<? super BaseResponse<T>> subscriber;
    private final Class<T> tClass;
    private boolean keepCache;
    private boolean takeCache;

    RequestProducer(Call call, Subscriber<? super BaseResponse<T>> subscriber, Class<T> tClass, boolean keepCache, boolean takeCache) {
        this.call = call;
        this.subscriber = subscriber;
        this.tClass = tClass;
        this.keepCache = keepCache;
        this.takeCache = takeCache;
    }

    /**
     * 生产事件,将同步请求转化为Rx的事件
     */
    @Override
    public void request(long n) {
        if (n <= 0) return; // Nothing to do when requesting 0.
        if (!compareAndSet(false, true)) return; // Request was already triggered.
        String url = call.request().url().toString();
        boolean hasCache = false;
//        //获取缓存
        if (takeCache) {
            HttpCache httpCache = Realm.getDefaultInstance().where(HttpCache.class)
                    .equalTo("url", url).limit(1).findFirst();
            if (httpCache != null) {
                try {
                    T cache = null;
                    cache = new Gson().fromJson(httpCache.getResponse(), tClass);
                    if (cache != null) {
                        if (!subscriber.isUnsubscribed()) {
                            hasCache = true;
                            subscriber.onNext(BaseResponse.create(true, cache));
                        }
                    }
                } catch (Exception e) {
                    Log.i("", "");
                }
            }
        }
        HttpCache httpCache = new HttpCache();
        httpCache.setUrl(url);
        //获取网络
        if (!call.isCanceled()) {
            try {
                Response response = call.execute();
                if (response.isSuccessful()) {
                    T result = null;
                    try {
                        String json = response.body().string();
                        result = new Gson().fromJson(json, tClass);
                        httpCache.setResponse(json);
                    } catch (Exception e) {
                        Log.i("", "");
                    }
                    if (!subscriber.isUnsubscribed()) {
                    }
                    subscriber.onNext(BaseResponse.create(false, result));
                    if (keepCache) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.insertOrUpdate(httpCache);
                        realm.commitTransaction();
                    }
                } else {
                    subscriber.onError(new RequestException(response.code(), response.message()));
                }

            } catch (Throwable t) {
                if (!subscriber.isUnsubscribed() && !hasCache) {
                    subscriber.onError(t);
                }
            }
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    @Override
    public void unsubscribe() {

        if (!call.isExecuted()) {
            call.cancel();
        }
    }

    @Override
    public boolean isUnsubscribed() {
        return subscriber.isUnsubscribed();
    }
}

