package com.example.rotateworld;

import android.app.Application;

/**
 * Created by zhouying on 2017/4/1.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
