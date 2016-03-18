package com.example.biac.yifuwu;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by fanlei on 16/3/15.
 */
public class MyApplication extends Application{

    //全局使用一个client
    private  static OkHttpClient client;
    @Override
    public void onCreate() {
        client = new OkHttpClient();
        super.onCreate();


    }

    public OkHttpClient getClient(){
        return client;
    }
}
