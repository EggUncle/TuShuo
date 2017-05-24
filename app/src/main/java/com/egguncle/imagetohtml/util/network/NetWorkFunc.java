package com.egguncle.imagetohtml.util.network;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.model.json.ResultHtmlImage;
import com.egguncle.imagetohtml.model.json.ResultRoot;
import com.egguncle.imagetohtml.ui.activity.WebViewActivity;
import com.egguncle.imagetohtml.ui.fragment.FragmentStars;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by egguncle on 17-5-18.
 * 网络功能
 */

public class NetWorkFunc {


    private final static String TAG = "NetWorkFunc";
    private final static int DEFAULT_COUNT = 10;

    //网络请求URL  （网络服务器）
    //  private final static String BASE_URL = "http://112.74.53.14:8080/Img2htmlServer";
    private final static String SERVER_URL = "http://112.74.53.14:8080";
    //网络请求URL （本地调试）
    private final static String BASE_URL = "http://10.42.0.60:8080";
    //上传html文件
    private final static String UPLOAD_URL = BASE_URL + "/api/upload_html";
    //获取网站里的的页面 （访问静态文件)
    public final static String HTML_URL = BASE_URL + "/static/htmls/";
    //批量请求网站里的图说数据 /find 默认随机查询15个
    private final static String GET_HTML_DATA = BASE_URL + "/find";


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


//    public static List<HtmlImage> getHtmlsFromServer(int count){
//        //不在主线程里运行
//        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
//            throw new RuntimeException("this function should not run on main thread!");
//        }
//        return null;
//    }

    /**
     * 从服务器获取图说数据
     *
     */
    public static void getHtmlFromServer() {
        //不在主线程里运行
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new RuntimeException("this function should not run on main thread!");
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GET_HTML_DATA)
                .build();

        Response response = null;
        try {
            Log.i(TAG, "getHtmlFromServer: 发送请求");
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.i(TAG, "getHtmlFromServer: " + response.code());
            } else {
                Log.i(TAG, "getHtmlFromServer: 请求成功");
                // Headers responseHeaders = response.headers();
//                for (int i = 0; i < responseHeaders.size(); i++) {
//                    Log.i(TAG, "getHtmlFromServer: "+responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }
              //  Log.i(TAG, "getHtmlFromServer: " + response.body().string());
                String json = response.body().string();
                Gson gson = new Gson();
                ResultRoot result = gson.fromJson(json, ResultRoot.class);
//                for (ResultHtmlImage htmlImage : result.getResults()) {
//                    int id=htmlImage.getId();
//                    String title = htmlImage.getTitle();
//                    String content=htmlImage.getContent();
//                    String htmlPath=htmlImage.getHtmlPath();
//                    String upLoadDate=htmlImage.getUploadDate();
//                    Log.i(TAG, "getHtmlFromServer: "+id+" "+title+" "+content+" "+htmlPath+" "+upLoadDate);
//                }
                if (!result.getError()){
                    Intent intent=new Intent(FragmentStars.STARS_BROADCAST);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("data",result);
                    intent.putExtra("data",bundle);

                    FragmentStars.getLocalBroadcastManager().sendBroadcast(intent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
