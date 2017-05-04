package com.egguncle.imagetohtml.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
        webview.setDrawingCacheEnabled(true);
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
        title = getIntent().getStringExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_generate_img) {
//            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();
//            View view=getWindow().getDecorView();
//            int uiOptions=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN;
//            view.setSystemUiVisibility(uiOptions);

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


}
