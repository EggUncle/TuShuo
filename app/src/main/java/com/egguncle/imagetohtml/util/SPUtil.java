package com.egguncle.imagetohtml.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by egguncle on 17-5-4.
 * 用来记录是否是第一次启动来判断是否要进行开场动画
 */

public class SPUtil {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SPUtil instance;
    private static final String SP_NAME = "TuShuo";
    private static final String IS_FIRST= "FIRST";

    private SPUtil() {

    }

    public static SPUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SPUtil.class) {
                instance = new SPUtil();
                mSharedPreferences = context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
                mEditor = mSharedPreferences.edit();
            }
        }
        return instance;
    }


    public boolean isFirst() {
        return mSharedPreferences.getBoolean(IS_FIRST, true);
    }

    public void recordingLaunch(){
        mEditor.putBoolean(IS_FIRST,false).commit();
    }

}
