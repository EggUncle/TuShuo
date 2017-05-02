package com.egguncle.imagetohtml.util;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.egguncle.imagetohtml.MyApplication;


/**
 * Created by egguncle on 17-2-13.
 */

public class ImageUtil {

    private final static String TAG = "ImageUtil";


    /**
     * @param data
     * @return 图片路径
     */
    @TargetApi(19)
    public static String handleImageOnKitKat(Intent data) {
        String imagePath = "";
        Log.i(TAG, "handleImageOnKitKat: ");

        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(MyApplication.getContext(), uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的ID
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通的方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    public static String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
       return imagePath;
    }

    private static String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取图片的真实路径
        Cursor cursor = MyApplication.getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }



}
