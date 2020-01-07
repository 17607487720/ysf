package com.example.myapplication.utils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {
    private static final RetrofitUtils ourInstance = new RetrofitUtils();
    private static String HTTP_TAG = "OkGo";
    private static boolean isDebugModel = true;

    String urlAddress = "";

    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        return ourInstance;
    }

    public Retrofit getUserRetrofit(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlAddress)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClicnt(context))
                .build();
        return retrofit;
    }


    public OkHttpClient getClicnt(final Context context) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        if (isDebugModel) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(HTTP_TAG);
            //日志的打印范围
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            //在logcat中的颜色
            loggingInterceptor.setColorLevel(Level.INFO);
            //默认是Debug日志类型
            httpBuilder.addInterceptor(loggingInterceptor);
        }
        httpBuilder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //读取超时时间
        httpBuilder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //写入超时时间
        httpBuilder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //连接超时时间

        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = null;
                String token = "";
                request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("KF_APP", "Android")
                        .header("KF_USER_TOKEN", token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        return httpBuilder.build();
    }
}

