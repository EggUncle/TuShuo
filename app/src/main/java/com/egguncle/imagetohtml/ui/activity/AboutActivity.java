package com.egguncle.imagetohtml.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
