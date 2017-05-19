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
    private final static String BASE_URL = "http://112.74.53.14:8080/Img2htmlServer";
    private final static String UPLOAD_URL =BASE_URL+ "/api/upload_html";
    public final static String HTML_URL=BASE_URL+"/htmls/";

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
        String name=htmlFile.getName();
  //      File imgFile = new File(imgPath);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", content)
                .addFormDataPart("title", title)
              //  .addFormDataPart("img_file", "test.png", RequestBody.create(MEDIA_TYPE_PNG, null))
                .addFormDataPart("html_file",name,RequestBody.create(null,htmlFile))
                .build();
        Request request = new Request.Builder().url(UPLOAD_URL).post(requestBody).build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()){
                Log.i(TAG, "upLoadHtml: 请求失败 \n");
                Log.i(TAG, "upLoadHtml: message:  "+response.message());
                Log.i(TAG, "upLoadHtml: body:  "+response.body().string());

            }else{
                Log.i(TAG, "upLoadHtml: 请求成功");
                Log.i(TAG, "upLoadHtml: "+response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
