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

    /**
     * @param c 内容
     * @param t 标题
     * @param p html路径
     */
    public UpLoadHtmlRunnable(String c, String t, String p) {
        content = c;
        title = t;
        htmlPath = p;
    }


    @Override
    public void run() {
        NetWorkFunc.upLoadHtml(content, title, htmlPath);
    }
}
