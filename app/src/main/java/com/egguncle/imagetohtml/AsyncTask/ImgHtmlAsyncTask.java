/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-27 上午10:47
 *
 */

package com.egguncle.imagetohtml.AsyncTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.egguncle.imagetohtml.MyApplication;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.model.RgbColor;
import com.egguncle.imagetohtml.ui.fragment.FragmentHome;
import com.egguncle.imagetohtml.util.file.FileUtil;
import com.egguncle.imagetohtml.util.img.Image2Html;
import com.egguncle.imagetohtml.util.network.NetUtil;
import com.egguncle.imagetohtml.util.network.NetWorkFunc;
import com.egguncle.imagetohtml.util.file.SPUtil;

import java.util.UUID;

/**
 * Created by egguncle on 17-5-2.
 * 用于异步加载html图片代码生成过程
 */

public class ImgHtmlAsyncTask extends AsyncTask<String, Integer, HtmlImage> {
    private final static String TAG = "ImgHtmlAsyncTask";


    /**
     * 执行中
     *
     * @param params
     * @return
     */
    @Override
    protected HtmlImage doInBackground(String... params) {
        Log.i(TAG, "doInBackground: ");

        String filePath = params[0];
        String title = params[1];
        String content = params[2];
        String htmlStr = null;

        //生成随机文件名称
//        long time = System.currentTimeMillis();
//        Random random = new Random(47);
//        String htmlName = time + "" + random.nextInt() * 1000 + ".html";
        //生成uuid作为文件名称
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String htmlName=uuid+".html";
        //生成文件的路径
        String htmlPath = FileUtil.getFilePath(htmlName);
        //如果当前的图片出于实验性模式，则不上传图片
        boolean isLaboratory= SPUtil.getInstance(MyApplication.getContext()).isLaboratory();
        int laboratory=isLaboratory?1:0;
        //先在列表中显示出该item
        //将文件保存到数据库中
        HtmlImage htmlImage = new HtmlImage();
        htmlImage.setHtmlName(htmlName);
        htmlImage.setHtmlPath(htmlPath);
        htmlImage.setContent(content);
        htmlImage.setTitle(title);
        htmlImage.setImgPath(filePath);
        htmlImage.setIsLaboratory(laboratory);
        htmlImage.save();

        //将htmlImg实例包装到bundle中，使用广播发送出去
        Intent intent = new Intent(FragmentHome.HOME_BROADCAST);
        intent.putExtra("type", "add_item");
        Bundle bundle = new Bundle();
        bundle.putSerializable("htmlImg", htmlImage);
        intent.putExtra("data", bundle);
        FragmentHome.getLocalBroadcastManager().sendBroadcast(intent);


        //生成html内容的字符串
        if (params.length >= 7) {
            int rgbDebug = Integer.parseInt(params[3]);
            int txtSize = Integer.parseInt(params[4]);
            int imgSize = Integer.parseInt(params[5]);
            int rgbR = Integer.parseInt(params[6]);
            int rgbG=Integer.parseInt(params[7]);
            int rgbB=Integer.parseInt(params[8]);

            htmlStr = Image2Html.imageToHtml(filePath, title, content,  new RgbColor(rgbR,rgbG,rgbB), txtSize, imgSize, rgbDebug);
        } else {
            htmlStr = Image2Html.imageToHtml(filePath, title, content);
        }


        //将html代码保存至对应路径
        FileUtil.creatFile(htmlPath, htmlStr);
        if (!isLaboratory) {
            //当不是实验性模式时，将文件上传到服务器中
          //  NetWorkFunc.upLoadHtml(content, title, htmlPath);
            NetUtil.upLoadHtmlFile(content, title, htmlPath,filePath);
        }

        return htmlImage;
    }

    /**
     * 执行前
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    /**
     * 执行后
     */
    @Override
    protected void onPostExecute(HtmlImage htmlImage) {
        super.onPostExecute(htmlImage);

        String htmlPath = htmlImage.getHtmlPath();
        String title = htmlImage.getTitle();
        String content = htmlImage.getContent();
        Intent intent = new Intent(FragmentHome.HOME_BROADCAST);
        intent.putExtra("type", "save_file_finish");
        intent.putExtra("file_path", htmlPath);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        FragmentHome.getLocalBroadcastManager().sendBroadcast(intent);
    }


}
