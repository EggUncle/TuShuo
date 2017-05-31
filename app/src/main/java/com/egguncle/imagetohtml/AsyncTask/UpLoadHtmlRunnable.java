/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-24 下午2:36
 *
 */

package com.egguncle.imagetohtml.AsyncTask;

import com.egguncle.imagetohtml.util.network.NetWorkFunc;

/**
 * Created by egguncle on 17-5-24.
 * 用于上传的runnable
 */

public class UpLoadHtmlRunnable implements Runnable {
    private String content;
    private String title;
    private String htmlPath;
    private String imgPath;

    /**
     * @param c 内容
     * @param t 标题
     * @param p html路径
     */
    public UpLoadHtmlRunnable(String c, String t, String p,String i) {
        content = c;
        title = t;
        htmlPath = p;
        imgPath=i;
    }


    @Override
    public void run() {
        NetWorkFunc.upLoadHtml(content, title, htmlPath,imgPath);
    }
}
