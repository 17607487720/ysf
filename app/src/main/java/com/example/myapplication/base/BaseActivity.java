package com.example.myapplication.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmonbaby.utils.net.NetType;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {
    /** subscriptions记录一系列正在进行请求的Subscription。然后用来取消这些订阅。 */
    private CompositeDisposable mCompositeDisposable;

    protected App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = App.getInstance();
        app.joinNetListener(this);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void hideBar() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    /** 默认返回销毁 */
    protected void goBack() {
        finish();
    }

    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /** 网络连接连接时调用 */
    protected void onConnect(NetType type) {
        reLoad();
    }

    /** 当前没有网络连接 */
    protected void onDisConnect() {
    }

    /** 网络重连时，重新加载 */
    protected void reLoad() {
    }

    @Override
    public void onDestroy() {
        /** 如果Activity被销毁了，那么就取消订阅。 */
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
        super.onDestroy();
    }
}
