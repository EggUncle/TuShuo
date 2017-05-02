package com.egguncle.imagetohtml.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by egguncle on 17-4-28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initVar();
        initAction();
    }

    abstract void initView();
    abstract void initAction();
    abstract void initVar();

    abstract int getLayoutId();
}
