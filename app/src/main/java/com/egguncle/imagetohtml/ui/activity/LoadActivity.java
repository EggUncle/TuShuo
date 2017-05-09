package com.egguncle.imagetohtml.ui.activity;


import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.util.SPUtil;

public class LoadActivity extends BaseActivity {
    private ImageView ivLoadLogo;
    private TextView tvLoad1;
    private TextView tvLoad2;

    private final static String TAG="LoadActivity";

    @Override
    void initView() {
        ivLoadLogo = (ImageView) findViewById(R.id.iv_load_logo);
        tvLoad1 = (TextView) findViewById(R.id.tv_load_1);
        tvLoad2 = (TextView) findViewById(R.id.tv_load_2);

    }

    @Override
    void initAction() {
        SPUtil spUtil = SPUtil.getInstance(this);
        //如果是第一次打开APP，则进行开场动画
        if (spUtil.isFirst()) {
            Log.i(TAG, "initAction: this is first launch");
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Animation imgAnimation = AnimationUtils.loadAnimation(this, R.anim.img_anim);
            imgAnimation.setFillAfter(true);
            ivLoadLogo.startAnimation(imgAnimation);

            final Animation tvAnimation = AnimationUtils.loadAnimation(this, R.anim.iv_anim);
            final Animation tvAnimation2 = AnimationUtils.loadAnimation(this, R.anim.iv_anim);

            tvAnimation.setFillAfter(true);
            tvAnimation2.setFillAfter(true);
            tvLoad1.setVisibility(View.INVISIBLE);
            tvLoad2.setVisibility(View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvLoad1.setVisibility(View.VISIBLE);
                    tvLoad1.startAnimation(tvAnimation);
                }
            }, 1000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvLoad2.setVisibility(View.VISIBLE);
                    tvLoad2.startAnimation(tvAnimation2);
                }
            }, 2000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoadActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.page_in_anim, R.anim.page_out_anim);
                    finish();
                }
            }, 3000);
        }else{
            //如果打开过app，则不播放动画直接打开
            startActivity(new Intent(LoadActivity.this, HomeActivity.class));
            finish();
        }
    }

    @Override
    void initVar() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024/1024);
        Log.d("TAG", "Max memory is " + maxMemory + "M");

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_load;
    }
}
