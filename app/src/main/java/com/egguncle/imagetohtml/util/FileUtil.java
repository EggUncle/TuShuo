package com.egguncle.imagetohtml.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.egguncle.imagetohtml.ui.activity.HomeActivity;
import com.egguncle.imagetohtml.ui.fragment.FragmentHome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by egguncle on 17-4-23.
 */

public class FileUtil {

    private final static String TAG = "FileUtil";

    public FileUtil() {
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
     * @param fileName 文件名称
     * @param writeStr 生成的HTML代码
     */
    public void creatFile(String fileName, String writeStr) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            try {
                Log.i(TAG, "creatFile: " + sdCardDir.getCanonicalPath() + "/image2Html/" + fileName);
                File file = new File(sdCardDir.getCanonicalPath() + "/image2Html/" + fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(writeStr.getBytes());
                raf.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile(String writeStr) {


    }

    /**
     * 获取文件路径
     *
     * @param fileName
     * @return
     */
    public static String getFileUrl(String fileName) {
        String url = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            try {
                url = sdCardDir.getCanonicalPath() + "/image2Html/" + fileName;
                Log.i(TAG, "getFileUrl: " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return url;
    }

    /**
     * 将图片转换为html文件，并保存
     *
     * @param filePath 图片文件路径
     * @param title    html的标题
     * @param content  填充的文字
     * @return html文件路径
     */
    public String saveFile(final String filePath, final String title, final String content) {
        long time = System.currentTimeMillis();
        final String htmlName = time + ".html";

        new Thread(new Runnable() {
            @Override
            public void run() {
                Image2Html image2Html = new Image2Html();
                String htmlStr = image2Html.imageToHtml(filePath, 10, title, content);
                FileUtil fileUtil = new FileUtil();
                fileUtil.creatFile(htmlName, htmlStr);

                Intent intent = new Intent(FragmentHome.HOME_BROADCAST);
                intent.putExtra("type","save_file_finish");
                FragmentHome.getLocalBroadcastManager().sendBroadcast(intent);
            }
        }).start();


        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            try {
                path = sdCardDir.getCanonicalPath() + "/image2Html/" + htmlName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }

    public static void saveBitmap(final Bitmap bitmap){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
                {    // 获取SDCard指定目录下
                    String sdCardDir = Environment.getExternalStorageDirectory() + "/image2Html/image/";
                    File dirFile = new File(sdCardDir);  //目录转化成文件夹
                    if (!dirFile.exists()) {             //如果不存在，那就建立这个文件夹
                        dirFile.mkdirs();
                    }
                    File file = new File(sdCardDir, System.currentTimeMillis() + ".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名
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
                }
            }
        }).start();

    }


}
