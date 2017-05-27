/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-23 上午10:20
 *
 */

package com.egguncle.imagetohtml.ui.activity;

import android.support.v7.app.ActionBar;

import com.egguncle.imagetohtml.R;

public class AboutActivity extends BaseActivity {


    @Override
    void initView() {

    }

    @Override
    void initAction() {
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    void initVar() {

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_about;
    }
}
