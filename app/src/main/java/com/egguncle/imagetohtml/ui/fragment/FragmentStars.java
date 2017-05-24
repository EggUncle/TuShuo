package com.egguncle.imagetohtml.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.ui.view.TextStars;

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

    public FragmentStars() {
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

        final Handler handler=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run() {
                textStars.add("测试");
                handler.postDelayed(this,TIME);
            }
        };
        handler.postDelayed(r,TIME);


    }

    private void initVar(){

    }
    private void initAction(){

    }


}
