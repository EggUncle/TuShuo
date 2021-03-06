/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-23 上午10:23
 *
 */

package com.egguncle.imagetohtml;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by egguncle on 17-4-29.
 */

public class MyApplication extends LitePalApplication {

    //用于全局的上下文对象
    private static Context context;

    private final static String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //获取context
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
