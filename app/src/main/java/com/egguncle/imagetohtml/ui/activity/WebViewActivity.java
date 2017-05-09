package com.egguncle.imagetohtml.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;

import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.ui.fragment.FragmentHome;
import com.egguncle.imagetohtml.util.FileUtil;



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


    //广播相关
    private WebViewActivity.WebReceiver webReceiver;
    public static LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;
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
        title = getIntent().getStringExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //广播相关
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
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

            Picture snapShot = webview.capturePicture();
           // Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
            Bitmap bmp = webview.getDrawingCache();
            Canvas canvas = new Canvas(bmp);
            snapShot.draw(canvas);
            FileUtil.saveBitmap(bmp);



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(webReceiver);
    }

    private class WebReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String path=intent.getStringExtra("path");
            Snackbar.make(webview,"已保存至:"+path,Snackbar.LENGTH_SHORT).show();
        }
    }

    public static LocalBroadcastManager getLocalBroadcastManager(){
        return localBroadcastManager;
    }
}
