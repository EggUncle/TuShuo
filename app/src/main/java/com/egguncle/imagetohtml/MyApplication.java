package com.egguncle.imagetohtml;

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
