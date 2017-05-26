package com.egguncle.imagetohtml.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by egguncle on 17-5-23.
 * 自定义viewpager 用于处理星海页面内容和viewpager的滑动冲突问题
 */

public class MyViewPager extends ViewPager {
    private final static String TAG = "MyViewPager";
    private int screenWidth;
    private float lastY;

    private final static int DISTANCE = 100;


    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        screenWidth = dm.widthPixels;
        Log.i(TAG, "MyViewPager: "+screenWidth);
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        return true;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
     //   Log.i(TAG, "onInterceptTouchEvent: ------------------");
        boolean result=false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
               // Log.i(TAG, "onInterceptTouchEvent: down");
                //触摸事件的分发控制，如果滑动的是屏幕边缘的部分，将事件传递给viewpager，
                //如果是中间一些的部分，则传递给里面的fragment
                float x = ev.getX();
                //  Log.i(TAG, "onInterceptTouchEvent: " + x);
                if (x<DISTANCE||screenWidth-x<DISTANCE){
                    result=true;
                }else{
                    result=false;
                }

                lastY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
               // Log.i(TAG, "onInterceptTouchEvent: up");
                break;
        }

        return result;
    }
}
