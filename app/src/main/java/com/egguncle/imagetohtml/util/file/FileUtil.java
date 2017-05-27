/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-24 下午2:36
 *
 */

package com.egguncle.imagetohtml.util.file;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.egguncle.imagetohtml.MyApplication;
import com.egguncle.imagetohtml.ui.activity.WebViewActivity;
import com.egguncle.imagetohtml.ui.fragment.FragmentHome;
import com.egguncle.imagetohtml.util.img.Image2Html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Created by egguncle on 17-4-23.
 */

public class FileUtil {

    private final static String TAG = "FileUtil";

    private FileUtil() {

    }

    public static void makeDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            try {
                File file = new File(sdCardDir.getCanonicalPath() + "/image2Html");
                if (!file.exists()) {
                    file.mkdir();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存文件
     *
     * @param url      存储的文件的路径
     * @param writeStr 生成的HTML代码
     */
    public static void creatFile(String url, String writeStr) {
        //该方法为耗时操作，不应在主线程
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new RuntimeException("this function should not run on main thread!");
        }
        FileUtil.makeDir();
        try {
            Log.i(TAG, "creatFile: " + url);
            File file = new File(url);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(writeStr.getBytes());
            raf.close();

           // return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // return null;
    }

    public void writeFile(String writeStr) {


    }

    /**
     * 获取文件路径
     *
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName) {
        FileUtil.makeDir();
        String filePath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            try {
                filePath = sdCardDir.getCanonicalPath() + "/image2Html/" + fileName;
                Log.i(TAG, "getFilePath: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return filePath;
    }

    /**
     * 将图片转换为html文件，并保存
     *
     * @param filePath 图片文件路径
     * @param title    html的标题
     * @param content  填充的文字
     * @return html文件路径
     */
    public static String getSaveFile(final String filePath, final String title, final String content) {
        FileUtil.makeDir();
        //生成随机文件名称
        long time = System.currentTimeMillis();
        Random random = new Random();
        final String htmlName = time + "" + random.nextInt() * 1000 + ".html";

        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            try {
                path = sdCardDir.getCanonicalPath() + "/image2Html/" + htmlName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // return path;
        final String htmlPath = path;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //转换图片并保存
                String htmlStr = Image2Html.imageToHtml(filePath, title, content);
                FileUtil.creatFile(htmlName, htmlStr);

                Intent intent = new Intent(FragmentHome.HOME_BROADCAST);
                intent.putExtra("type", "save_file_finish");
                intent.putExtra("file_path", htmlPath);
                intent.putExtra("title", title);
                intent.putExtra("content", content);


                FragmentHome.getLocalBroadcastManager().sendBroadcast(intent);
            }
        }).start();


        return path;
    }

    public static void saveBitmap(final Bitmap bitmap) {
        FileUtil.makeDir();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
                {    // 获取SDCard指定目录下
                    String sdCardDir = Environment.getExternalStorageDirectory() + "/Pictures/图说/";
                    File dirFile = new File(sdCardDir);  //目录转化成文件夹
                    if (!dirFile.exists()) {             //如果不存在，那就建立这个文件夹
                        dirFile.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File file = new File(sdCardDir, fileName);// 在SDcard的目录下创建图片文,以当前时间为其命名
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onOptionsItemSelected: save success");
                    Log.i(TAG, "run: " + file.getPath());
                    Intent intent = new Intent(WebViewActivity.WEB_ACT_BROADCAST);
                    intent.putExtra("type","save_file");
                    intent.putExtra("path", file.getPath());
                    WebViewActivity.getLocalBroadcastManager().sendBroadcast(intent);


                    // 保存后扫描一下文件，及时更新到系统目录
                    MediaScannerConnection.scanFile(MyApplication.getContext(),
                            new String[]{sdCardDir + fileName}, null, null);
                    //    MyApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getPath())));
                }
            }
        }).start();


    }


}
