/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-26 下午4:02
 *
 */

package com.egguncle.imagetohtml.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.util.file.FileUtil;
import com.egguncle.imagetohtml.util.network.NetUtil;
import com.egguncle.imagetohtml.util.network.NetWorkFunc;

import org.litepal.crud.DataSupport;

/**
 * 点击星海页面后显示的webview
 */
public class StarWebViewActivity extends BaseActivity {

    private WebView webviewStar;
    private final static String TAG="StarWebViewActivity";

    private  String url;

    /**
     * 启动该activity
     *
     * @param context
     * @param title    html标题
     * @param htmlName html文件的名称
     */
    public static void startStarWebViewActivity(Context context, String title, String htmlName) {
        Intent intent = new Intent(context, StarWebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("htmlName", htmlName);
        context.startActivity(intent);
    }

    @Override
    void initView() {
        webviewStar = (WebView) findViewById(R.id.webview_star);
    }

    @Override
    void initAction() {

    }

    @Override
    void initVar() {
        String title = getIntent().getStringExtra("title");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        String htmlName = getIntent().getStringExtra("htmlName");
        WebSettings setting = webviewStar.getSettings();
        //指持获取手势焦点
        webviewStar.requestFocusFromTouch();
        webviewStar.setDrawingCacheEnabled(true);
        //支持缩放
        setting.setSupportZoom(true);
        //自适应
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
        //设置内置的缩放控件。
        setting.setBuiltInZoomControls(true);
        //隐藏缩放控件
        setting.setDisplayZoomControls(false);

         url = NetWorkFunc.HTML_URL + htmlName + ".html";
        webviewStar.loadUrl(url);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_star_web_view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_star, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
//            case R.id.action_generate_img: {
//                //保存图片
//                //    Picture snapShot = webview.capturePicture();
//                //    Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
//                //获取webview缩放率
//                float scale = webviewStar.getScale();
//                //得到缩放后webview内容的高度
//                int webViewHeight = (int) (webviewStar.getContentHeight() * scale);
//                Log.i(TAG, "onOptionsItemSelected: " + webviewStar.getContentHeight());
//                Log.i(TAG, "onOptionsItemSelected: " + webViewHeight);
//                // Bitmap bitmap = webview.getDrawingCache();
//
//                //生成图片并绘制
//                Bitmap bitmap = Bitmap.createBitmap(webviewStar.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
//                Log.i(TAG, "onOptionsItemSelected: " + bitmap.getWidth() + " " + bitmap.getHeight());
//                Canvas canvas = new Canvas(bitmap);
//                webviewStar.draw(canvas);
//                //    int imgH=bitmap.getHeight();
//                int imgW = bitmap.getWidth();
//
//                //加载原图的宽高，进行相关计算，对最后生成的图片进行裁切
//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                opts.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(imgPath, opts);
//                int height = opts.outHeight;
//                int width = opts.outWidth;
//                Log.i(TAG, "onOptionsItemSelected: " + width + " " + height);
//                //计算裁切比率
//                float cut = (float) height / (float) width;
//                int imgH = (int) (imgW * cut);
//
//                Bitmap saveBmp = Bitmap.createBitmap(bitmap, 0, 0, imgW, imgH);
//                FileUtil.saveBitmap(saveBmp);
//
//
//            }
//            break;
            case R.id.action_geturl:{
                //复制链接
                ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("test",url);
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make(webviewStar, getResources().getString(R.string.copy_link), Snackbar.LENGTH_SHORT).show();
            }break;

        }
        return super.onOptionsItemSelected(item);
    }

}
