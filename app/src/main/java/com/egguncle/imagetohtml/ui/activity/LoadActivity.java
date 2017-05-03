package com.egguncle.imagetohtml.ui.activity;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.egguncle.imagetohtml.R;

public class LoadActivity extends BaseActivity {
    private ImageView ivLoadLogo;
    private TextView tvLoad1;
    private TextView tvLoad2;


    @Override
    void initView() {
        ivLoadLogo = (ImageView) findViewById(R.id.iv_load_logo);
        tvLoad1 = (TextView) findViewById(R.id.tv_load_1);
        tvLoad2 = (TextView) findViewById(R.id.tv_load_2);

    }

    @Override
    void initAction() {
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
               startActivity(new Intent(LoadActivity.this,HomeActivity.class));
                overridePendingTransition(R.anim.page_in_anim,R.anim.page_out_anim);
            }
        }, 3000);

    }

    @Override
    void initVar() {

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_load;
    }
}
