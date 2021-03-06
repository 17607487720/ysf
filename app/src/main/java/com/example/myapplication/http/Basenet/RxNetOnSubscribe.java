package com.example.myapplication.http.Basenet;

import com.example.myapplication.model.BaseResponse;
import com.example.myapplication.utils.RxUtils;

import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;

public class RxNetOnSubscribe<T> implements Observable.OnSubscribe<BaseResponse<T>> {

    private final Call call;
    private boolean keepCache;
    private boolean takeCache;
    private final Class<T> tClass;

    public static <T> RxNetOnSubscribe<T> create(Call call, Class<T> tClass) {
        return new RxNetOnSubscribe<T>(call, tClass);
    }

    public RxNetOnSubscribe<T> enableCache(boolean enable) {
        return this.takeCache(enable).keepCache(enable);
    }

    public RxNetOnSubscribe<T> takeCache(boolean takeCache) {
        this.takeCache = takeCache;
        return this;
    }

    public RxNetOnSubscribe<T> keepCache(boolean keepCache) {
        this.keepCache = keepCache;
        return this;
    }

    public RxNetOnSubscribe(Call call, Class<T> tClass) {
        this(call, tClass, false, false);
    }

    public RxNetOnSubscribe(Call call, Class<T> tClass, boolean takeCache) {
        this(call, tClass, takeCache, takeCache);
    }

    public RxNetOnSubscribe(Call call, Class<T> tClass, boolean keepCache, boolean takeCache) {
        this.call = call;
        this.tClass = tClass;
        this.keepCache = keepCache;
        this.takeCache = takeCache;
    }

    @Override
    public void call(Subscriber<? super BaseResponse<T>> subscriber) {
        RequestProducer<T> requestArbiter = new RequestProducer<T>(call, subscriber, tClass, keepCache, takeCache);
        subscriber.add(requestArbiter);
        subscriber.setProducer(requestArbiter);
    }

    public Observable<BaseResponse<T>> toRx() {
        return Observable.create(this);
    }

    public Observable<BaseResponse<T>> toRxMain() {
        return Observable.create(this).compose(RxUtils.subscribeInMain());
    }
}

