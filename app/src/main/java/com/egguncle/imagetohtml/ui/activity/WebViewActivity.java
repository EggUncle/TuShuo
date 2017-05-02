package com.egguncle.imagetohtml.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.egguncle.imagetohtml.R;

/**
 * 显示html的界面
 */
public class WebViewActivity extends BaseActivity {

    private final static String TAG = "WebViewActivity";

    private WebView webview;
    //要去加载的html地址
    private String htmlUrl;
    //标题
    private String title;

    @Override
    void initView() {
        webview = (WebView) findViewById(R.id.webview);

    }


    void initAction() {
        WebSettings setting = webview.getSettings();
        //指持获取手势焦点
        webview.requestFocusFromTouch();
        //支持缩放
        setting.setSupportZoom(true);
        //自适应
        setting.setLoadWithOverviewMode(true);
        //设置内置的缩放控件。
        setting.setBuiltInZoomControls(true);
        //隐藏缩放控件
        setting.setDisplayZoomControls(false);

        webview.loadUrl("file:///" + htmlUrl);
        Log.i(TAG, "initAction: " + htmlUrl);
    }

    @Override
    void initVar() {
        htmlUrl = getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_web_view;
    }
}
