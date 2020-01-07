package com.example.myapplication.http;

import com.example.myapplication.model.BaseEntity;
import com.example.myapplication.model.BaseString;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 登录相关接口
 */
public interface LoginService {

    /**
     * 获取版本
     */
    @GET("version")
    Observable<BaseEntity<Object>> version();

    /**
     * 下载版本
     */
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String url);

    /**
     * 登录
     */
    @POST("login2")
    @FormUrlEncoded
    Observable<BaseEntity<Object>> login(@FieldMap Map<String, String> params);

    /**
     * 修改密码
     */
    @PUT("mobile/modify/password")
    Observable<BaseString> updatePassword(@Header("xauthToken") String token, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);


}
