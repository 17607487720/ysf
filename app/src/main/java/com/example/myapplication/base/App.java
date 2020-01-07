package com.example.myapplication.base;

import androidx.multidex.MultiDexApplication;

import com.cmonbaby.utils.net.NetChangeObserver;
import com.cmonbaby.utils.net.NetStateReceiver;
import com.cmonbaby.utils.net.NetType;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends MultiDexApplication {
    /** 网络是否已打开已获得 */
    protected boolean networkAvailable;
    /** 网络状态监听 */
    protected NetChangeObserver netObserver;
    /** 需要网络监听 */
    protected BaseActivity activity;

    /** 获取当前网络状态，true为网络连接成功，否则网络连接失败 */
    protected boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initNetWork();
        initRealm();
    }

    /** 加入网络监听 */
    protected void joinNetListener(BaseActivity activity) {
        this.activity = activity;
    }

    private void initRealm(){
        //初始化数据库
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("qiandao.realm")//指定数据库的名称。如不指定默认名为default。
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库，开发时候打开
//                .inMemory()// 声明数据库只在内存中持久化
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private void initNetWork() {
        netObserver = new NetChangeObserver() {

            @Override
            public void onConnect(NetType type) {
                /** 网络连接连接时调用 */
                networkAvailable = true;
                if (activity != null) activity.onConnect(type);
            }

            @Override
            public void onDisConnect() {
                /** 当前没有网络连接 */
                networkAvailable = false;
                if (activity != null) activity.onDisConnect();
            }
        };
        NetStateReceiver.registerObserver(netObserver);
    }
}
