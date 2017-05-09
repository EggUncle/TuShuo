package com.egguncle.imagetohtml.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.util.FileUtil;

import static android.webkit.WebView.enableSlowWholeDocumentDraw;


/**
 * 显示html的界面
 */
public class WebViewActivity extends BaseActivity {

    private final static String TAG = "WebViewActivity";

    private WebView webview;
    //要去加载的html地址
    private String htmlUrl;


    //广播相关
    private WebViewActivity.WebReceiver webReceiver;
    public static LocalBroadcastManager localBroadcastManager;
    public final static String WEB_ACT_BROADCAST = "com.egguncle.imagetohtml.WEB_ACT_BROADCAST";

    @Override
    void initView() {
        webview = (WebView) findViewById(R.id.webview);

    }


    void initAction() {
        WebSettings setting = webview.getSettings();
        //指持获取手势焦点
        webview.requestFocusFromTouch();
        webview.setDrawingCacheEnabled(true);

        //支持缩放
        setting.setSupportZoom(true);
        //自适应
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);

        //设置内置的缩放控件。
        setting.setBuiltInZoomControls(true);
        //隐藏缩放控件
        setting.setDisplayZoomControls(false);
        //缩放（自适应）
        //   setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        webview.loadUrl("file:///" + htmlUrl);
        Log.i(TAG, "initAction: " + htmlUrl);


    }

    @Override
    void initVar() {
        htmlUrl = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //广播相关
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WEB_ACT_BROADCAST);
        webReceiver = new WebViewActivity.WebReceiver();
        localBroadcastManager.registerReceiver(webReceiver, intentFilter);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //保存图片
        if (id == R.id.action_generate_img) {

            //    Picture snapShot = webview.capturePicture();
            //    Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
            //获取webview缩放率
            float scale = webview.getScale();
            //得到缩放后webview内容的高度
            int webViewHeight = (int) (webview.getContentHeight()*scale);
            Log.i(TAG, "onOptionsItemSelected: "+webview.getContentHeight());
            Log.i(TAG, "onOptionsItemSelected: "+webViewHeight);
           // Bitmap bitmap = webview.getDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(webview.getWidth(),webViewHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            webview.draw(canvas);
            //   snapShot.draw(canvas);


//            View view  = this.getWindow().getDecorView();
//            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            view.draw(canvas);


            FileUtil.saveBitmap(bitmap);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(webReceiver);
    }

    private class WebReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String path = intent.getStringExtra("path");
            Snackbar.make(webview, "已保存至:" + path, Snackbar.LENGTH_SHORT).show();
        }
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }
}
