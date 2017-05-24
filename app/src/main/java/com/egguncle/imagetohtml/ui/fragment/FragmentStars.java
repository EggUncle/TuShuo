package com.egguncle.imagetohtml.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.json.ResultHtmlImage;
import com.egguncle.imagetohtml.model.json.ResultRoot;
import com.egguncle.imagetohtml.ui.view.TextStars;
import com.egguncle.imagetohtml.util.network.NetUtil;
import com.egguncle.imagetohtml.util.network.NetWorkFunc;

/**
 * Created by egguncle on 17-4-30.
 * 星海页面 展示他人上传的图片转html文件
 */

public class FragmentStars extends Fragment {

    private final static String TAG = "FragmentStars";
    private final static String CONTEXT="context";

    private View rootView;
    private TextStars textStars;

    private final static int TIME=1000;

    //广播相关
    private FragmentStars.StarsReceiver starsReceiver;
    public static LocalBroadcastManager localBroadcastManager;
    public final static String STARS_BROADCAST = "com.egguncle.imagetohtml.STARS_BOROADCAST";


    public FragmentStars() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager=LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(STARS_BROADCAST);
        starsReceiver=new StarsReceiver();
        localBroadcastManager.registerReceiver(starsReceiver,intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_stars, container, false);
        initView();
        initVar();
        initAction();
        return rootView;
    }

    private void initView() {
        textStars= (TextStars) rootView.findViewById(R.id.text_stars);

        NetUtil.getHtmlFromServer();
    }

    private void initVar(){

    }
    private void initAction(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(starsReceiver);
    }


    private class StarsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ResultRoot resultRoot= (ResultRoot) intent.getBundleExtra("data").get("data");
            if (resultRoot!=null){
                for (ResultHtmlImage htmlIamge:resultRoot.getResults()) {
                            textStars.add(htmlIamge);

                }
            }
        }
    }
    public static LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }


}
