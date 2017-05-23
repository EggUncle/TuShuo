package com.egguncle.imagetohtml.util;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.ui.activity.WebViewActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by egguncle on 17-5-18.
 * 网络工具类
 */

public class NetUtil {


    private final static String TAG = "NetUtil";

    //网络请求URL  （网络服务器）
    // private final static String BASE_URL = "http://112.74.53.14:8080/Img2htmlServer";
    private final static String BASE_URL = "http://112.74.53.14:8080";
    //网络请求URL （本地调试）
    //  private final static String BASE_URL = "http://10.42.0.60:8080";
    private final static String UPLOAD_URL = "http://112.74.53.14:8080/Img2htmlServer" + "/api/upload_html";
    public final static String HTML_URL = BASE_URL + "/static/htmls/";

    private final static MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final static MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");


    /**
     * 上传html和图片
     *
     * @param content  填充文字的内容
     * @param title    标题
     * @param htmlPath 手机中html的文件路径
     */
    public static void upLoadHtml(String content, String title, String htmlPath) {
        //不在主线程里运行
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new RuntimeException("this function should not run on main thread!");
        }
        OkHttpClient okHttpClient = new OkHttpClient();

        File htmlFile = new File(htmlPath);
        String name = htmlFile.getName();
        //      File imgFile = new File(imgPath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", content)
                .addFormDataPart("title", title)
                //  .addFormDataPart("img_file", "test.png", RequestBody.create(MEDIA_TYPE_PNG, null))
                .addFormDataPart("html_file", name, RequestBody.create(null, htmlFile))
                .build();

        Intent intent = new Intent(WebViewActivity.WEB_ACT_BROADCAST);
        intent.putExtra("type", "upload");
        Request request = new Request.Builder().url(UPLOAD_URL).post(requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.i(TAG, "upLoadHtml: 上传失败 \n");
                Log.i(TAG, "upLoadHtml: message:  " + response.message());
                Log.i(TAG, "upLoadHtml: body:  " + response.body().string());
                intent.putExtra("success", false);
            } else {
                Log.i(TAG, "upLoadHtml: 上传成功");
                Log.i(TAG, "upLoadHtml: " + response.message());
                //上传后，在数据库中将对应数据的isupload状态更新为true
                HtmlImage htmlImage = new HtmlImage();
                htmlImage.setUpLoad(1);
                htmlImage.updateAll("htmlpath = ?", htmlPath);
                intent.putExtra("success", true);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "upLoadHtml: 上传失败 \n");
            intent.putExtra("success", false);
        }
        Log.i(TAG, "upLoadHtml: 发送广播");
        //给webviewactivity发送广播 通知这次上传操作的结果
        if (WebViewActivity.getLocalBroadcastManager() != null) {
            WebViewActivity.getLocalBroadcastManager().sendBroadcast(intent);
        }
    }


}
