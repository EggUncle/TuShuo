/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-24 下午2:36
 *
 */

package com.egguncle.imagetohtml.util.network;

import com.egguncle.imagetohtml.AsyncTask.GetHtmlFromServerRunnable;
import com.egguncle.imagetohtml.AsyncTask.UpLoadHtmlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by egguncle on 17-5-24.
 * 网络请求工具类
 */

public class NetUtil {

    //用于网络请求的线程池
    private static ExecutorService executorService= Executors.newFixedThreadPool(5);

    public static void upLoadHtmlFile(String content,String title,String htmlPath,String imgPath){
        executorService.execute(new UpLoadHtmlRunnable(content,title,htmlPath,imgPath));
    }

    public static void getHtmlFromServer(){
        executorService.execute(new GetHtmlFromServerRunnable());
    }
}
