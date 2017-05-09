package com.egguncle.imagetohtml.util;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by egguncle on 17-5-2.
 * 用于异步加载html图片代码生成过程
 */

public class ImgHtmlAsyncTask extends AsyncTask<String, Integer, String> {
    private final static String TAG = "ImgHtmlAsyncTask";


    /**
     * 执行中
     *
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        Log.i(TAG, "doInBackground: ");
        
        String filePath = params[0];
        String title = params[1];
        String content = params[2];
        FileUtil fileUtil = new FileUtil();


        return fileUtil.saveFile(filePath, title, content);
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
     *
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    /**
     * 进度返回
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.i(TAG, "onProgressUpdate: ");
        Log.i(TAG, "onProgressUpdate: " + values[0]);
    }


}
