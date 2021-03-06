package com.example.myapplication.model;

/**
 * server端异常实体
 */
public class BaseString {

    private String data; // 数据，可能是Object、数组、或者是分页对象
    private String err; //错误信息
    private boolean success;//请求是否正确，true代表http请求成功

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
