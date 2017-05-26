package com.egguncle.imagetohtml.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.util.file.FileUtil;
import com.egguncle.imagetohtml.util.network.NetUtil;
import com.egguncle.imagetohtml.util.network.NetWorkFunc;

import org.litepal.crud.DataSupport;


/**
 * 显示html的界面
 */
public class WebViewActivity extends BaseActivity {

    private final static String TAG = "WebViewActivity";

    private final static int ID_GET_LINK = 100;
    private final static int ID_UPLOAD = 101;

    private WebView webview;
    //要去加载的html地址
    private String htmlUrl;
    //图片路径
    private String imgPath;
    //html文件名
    private String htmlName;
    //html填充的文字
    private String content;
    //html页面的标题
    private String title;

    //广播相关
    private static WebViewActivity.WebReceiver webReceiver;
    public static LocalBroadcastManager localBroadcastManager;
    public final static String WEB_ACT_BROADCAST = "com.egguncle.imagetohtml.WEB_ACT_BROADCAST";

    @Override
    void initView() {
        webview = (WebView) findViewById(R.id.webview);

    }

    /**
     * 启动该activity的默认方法
     * @param context   
     * @param htmlUrl  html的地址
     * @param imgPath  图片的路径
     * @param htmlName  html文本件名称
     * @param content   html内的填充文字
     * @param title    html的标题
     */
    public static void startWebViewActivity(Context context,String htmlUrl,String imgPath,String htmlName,String content,String title){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", htmlUrl);
        intent.putExtra("title", title);
        intent.putExtra("imgpath",imgPath);
        intent.putExtra("html_name",htmlName);
        intent.putExtra("content",content);
        context.startActivity(intent);
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
        imgPath = getIntent().getStringExtra("imgpath");
        htmlName = getIntent().getStringExtra("html_name");
        content = getIntent().getStringExtra("content");
        title = getIntent().getStringExtra("title");
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
        //判断该html是否上传过
        HtmlImage htmlImage = DataSupport.limit(1)
                // .select("isupload and islaboratory")
                .where("htmlpath=?", htmlUrl)
                .find(HtmlImage.class)
                .get(0);
        Log.i(TAG, "onCreateOptionsMenu:\n "
                + htmlImage.getTitle() + "\n"
                + htmlImage.getContent() + "\n"
                + htmlImage.getHtmlPath() + "\n"
                + htmlImage.getHtmlName() + "\n"
                + htmlImage.getIsLaboratory() + "\n"
        );

        //通过判断是否出自实验性模式来确定是否给出复制链接和上传的按钮
        int isLaboratory = htmlImage.getIsLaboratory();
        if (isLaboratory == 0) {
            int isUpLoad = htmlImage.isUpLoad();
            Log.i(TAG, "onCreateOptionsMenu: " + isUpLoad);
            if (isUpLoad == 1) {
                //如果上传过，则在菜单中显示复制链接选项
                menu.add(Menu.NONE, ID_GET_LINK, Menu.NONE,
                        getResources()
                                .getString(R.string.copy_link))
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.icon_link))
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                Log.i(TAG, "onCreateOptionsMenu: this html page is upload");
            } else {
                //如果没有上传过，显示上传按钮
                menu.add(Menu.NONE, ID_UPLOAD, Menu.NONE,
                        getResources()
                                .getString(R.string.upload))
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.icon_upload))
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                Log.i(TAG, "onCreateOptionsMenu: this html page is not upload");
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_generate_img: {
                //保存图片
                //    Picture snapShot = webview.capturePicture();
                //    Bitmap bitmap = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_8888);
                //获取webview缩放率
                float scale = webview.getScale();
                //得到缩放后webview内容的高度
                int webViewHeight = (int) (webview.getContentHeight() * scale);
                Log.i(TAG, "onOptionsItemSelected: " + webview.getContentHeight());
                Log.i(TAG, "onOptionsItemSelected: " + webViewHeight);
                // Bitmap bitmap = webview.getDrawingCache();

                //生成图片并绘制
                Bitmap bitmap = Bitmap.createBitmap(webview.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
                Log.i(TAG, "onOptionsItemSelected: " + bitmap.getWidth() + " " + bitmap.getHeight());
                Canvas canvas = new Canvas(bitmap);
                webview.draw(canvas);
                //    int imgH=bitmap.getHeight();
                int imgW = bitmap.getWidth();

                //加载原图的宽高，进行相关计算，对最后生成的图片进行裁切
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imgPath, opts);
                int height = opts.outHeight;
                int width = opts.outWidth;
                Log.i(TAG, "onOptionsItemSelected: " + width + " " + height);
                //计算裁切比率
                float cut = (float) height / (float) width;
                int imgH = (int) (imgW * cut);

                Bitmap saveBmp = Bitmap.createBitmap(bitmap, 0, 0, imgW, imgH);
                FileUtil.saveBitmap(saveBmp);


            }
            break;
            case ID_GET_LINK: {
                //复制链接
                ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("test", NetWorkFunc.HTML_URL + htmlName);
                clipboardManager.setPrimaryClip(clipData);
                Snackbar.make(webview, getResources().getString(R.string.copy_link), Snackbar.LENGTH_SHORT).show();
            }
            break;
            case ID_UPLOAD: {
                //上传页面
                //判断该html是否上传过
                HtmlImage htmlImage = DataSupport.limit(1)
                        // .select("isupload and islaboratory")
                        .where("htmlpath=?", htmlUrl)
                        .find(HtmlImage.class)
                        .get(0);
                if (htmlImage.isUpLoad() == 0) {

//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            NetWorkFunc.upLoadHtml(content, title, htmlUrl);
//
//                        }
//                    }).start();
                    NetUtil.upLoadHtmlFile(content, title, htmlUrl);
                }else{
                    Snackbar.make(webview, getResources().getString(R.string.had_upload), Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
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
            String type = intent.getStringExtra("type");
            Log.i(TAG, "onReceive: " + type);
            if ("save_file".equals(type)) {
                String path = intent.getStringExtra("path");
                Snackbar.make(webview, "已保存至:" + path, Snackbar.LENGTH_SHORT).show();
            } else if ("upload".equals(type)) {

                boolean success = intent.getBooleanExtra("success", false);
                if (success) {
                    Log.i(TAG, "onReceive: upload success ");
                    Snackbar.make(webview, getResources().getString(R.string.upload_success), Snackbar.LENGTH_SHORT)
                            .setAction(getResources().getString(R.string.copy_link), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //复制链接
                                    ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clipData = ClipData.newPlainText("test", NetWorkFunc.HTML_URL + htmlName);
                                    clipboardManager.setPrimaryClip(clipData);
                                }
                            })
                            .setActionTextColor(Color.WHITE)
                            .show();
                } else {
                    Log.i(TAG, "onReceive: upload failed ");
                    Snackbar.make(webview, getResources().getString(R.string.upload_failed), Snackbar.LENGTH_SHORT)
                            .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            NetWorkFunc.upLoadHtml(content, title, htmlUrl);
//                                        }
//                                    }).start();
                                    NetUtil.upLoadHtmlFile(content, title, htmlUrl);
                                }
                            })
                            .setActionTextColor(Color.WHITE)
                            .show();
                }
            }
        }
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {

        return localBroadcastManager;
    }
}
