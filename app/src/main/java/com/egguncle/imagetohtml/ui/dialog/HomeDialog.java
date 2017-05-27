/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-24 下午2:36
 *
 */

package com.egguncle.imagetohtml.ui.dialog;

import android.content.Context;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.egguncle.imagetohtml.AsyncTask.ImgHtmlAsyncTask;
import com.egguncle.imagetohtml.R;

/**
 * Created by egguncle on 17-5-1.
 * home页面点击设置图片以后弹出的对话框
 */

public class HomeDialog extends BaseDialog {

    private final static String TAG = "HomeDialog";

    private Context mContext;

    private View rootView;

    private TextView tvImgpath;
    private TextInputLayout tilTitle;
    private TextInputLayout tilContent;
    private ImageView ivDialogImg;
    private String mImgPath;


    public HomeDialog(Context context) {
        mContext = context;
        createDialog(context);
    }

    @Override
    void createDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.dialog_home, null);
        new AlertDialog.Builder(context)
                .setView(rootView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = getInputTitle();
                        String content = getInputContent();
                        ImgHtmlAsyncTask imgHtmlAsyncTask = new ImgHtmlAsyncTask();
                        imgHtmlAsyncTask.execute(mImgPath, title, content);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();


        initView();
        initVar();
        initAction();
    }

    @Override
    void initView() {
        tvImgpath = (TextView) rootView.findViewById(R.id.tv_imgpath);
        tilTitle = (TextInputLayout) rootView.findViewById(R.id.til_title);
        tilContent = (TextInputLayout) rootView.findViewById(R.id.til_content);
        ivDialogImg = (ImageView) rootView.findViewById(R.id.iv_dialog_img);

    }

    @Override
    void initVar() {

    }

    @Override
    void initAction() {

    }

    /**
     * 获取输入的标题
     *
     * @return
     */
    @Override
    String getInputTitle() {
        return tilTitle.getEditText().getText().toString();
    }

    /**
     * 获取输入的填充文字
     *
     * @return
     */
    @Override
    String getInputContent() {
        return tilContent.getEditText().getText().toString();
    }

    /**
     * 设置获取到的图片
     *
     * @param imgPath
     */
    @Override
    public void setIvDialogImg(String imgPath) {
        Glide.with(mContext).load(imgPath).into(ivDialogImg);
    }

    /**
     * 显示图片路径
     *
     * @param imgpath
     */
    @Override
    public   void setTvImgpath(String imgpath) {
        tvImgpath.setText("图片路径:" + imgpath);
        mImgPath = imgpath;
    }
}
