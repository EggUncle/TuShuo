package com.egguncle.imagetohtml.util;

import android.os.Looper;
import android.util.Log;

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

    //网络请求URL
    private final static String BASE_URL = "10.42.0.60:8080";
    private final static String UPLOAD_URL = "/api/upload_html";

    private final static MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final static MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");


    /**
     * 上传html和图片
     *
     * @param content  填充文字的内容
     * @param title    标题
     * @param htmlPath 手机中html的文件路径
     * @param imgPath  图片路径
     */
    public static void upLoadHtml(String content, String title, String htmlPath, String imgPath) {
        //不在主线程里运行
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new RuntimeException("this function should not run on main thread!");
        }
        OkHttpClient okHttpClient = new OkHttpClient();

        File htmlFile = new File(htmlPath);
  //      File imgFile = new File(imgPath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", content)
                .addFormDataPart("title", title)
               // .addFormDataPart("img_file", "test.png", RequestBody.create(MEDIA_TYPE_PNG, imgFile))
                .addFormDataPart("html_file","test.html",RequestBody.create(null,htmlFile))
                .build();
        Request request = new Request.Builder().url(UPLOAD_URL).post(requestBody).build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()){
                Log.i(TAG, "upLoadHtml: 请求失败");
            }else{
                Log.i(TAG, "upLoadHtml: "+response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
