/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-27 上午10:47
 *
 */

package com.egguncle.imagetohtml.AsyncTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.egguncle.imagetohtml.MyApplication;
import com.egguncle.imagetohtml.model.json.ResultHtmlImage;
import com.egguncle.imagetohtml.ui.view.TextStars;

import java.util.List;

/**
 * Created by egguncle on 17-5-26.
 */

public class StarsAsyncTask extends AsyncTask<List<ResultHtmlImage>, Integer, String> {
    private static TextStars textStars;
    private final static int ADD_VIEW = 0x123;
    private final static String TAG = "StarsAsyncTask";
    private UIHandler handler;

    public StarsAsyncTask(TextStars t) {
        textStars = t;
        handler = new UIHandler();
    }


    @Override
    protected String doInBackground(List<ResultHtmlImage>... resultHtmlImages) {
        for (ResultHtmlImage htmlImage : resultHtmlImages[0]) {
            //  textStars.add(htmlImage);
            SystemClock.sleep(1000);
            if (htmlImage != null) {
                Bundle bundle = new Bundle();
                Log.i(TAG, "doInBackground: ");
                bundle.putSerializable("data", htmlImage);
                Message msg = Message.obtain();
                msg.obj = bundle;
                msg.what = ADD_VIEW;
                handler.sendMessage(msg);
            }
        }


        return null;
    }

    private static class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == ADD_VIEW) {
                Bundle bundle = (Bundle) msg.obj;
                ResultHtmlImage htmlImage = (ResultHtmlImage) bundle.get("data");
                textStars.addViewToViewGroup(htmlImage);
            }
        }
    }

}
