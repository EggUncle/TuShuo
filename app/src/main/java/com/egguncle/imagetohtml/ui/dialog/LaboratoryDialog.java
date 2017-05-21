package com.egguncle.imagetohtml.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.egguncle.imagetohtml.AsyncTask.ImgHtmlAsyncTask;
import com.egguncle.imagetohtml.R;


/**
 * Created by egguncle on 17-5-20.
 */

public class LaboratoryDialog extends BaseDialog {

    private final static String TAG = "HomeDialog";
    private final static int MIN_TXT_SIZE = 1;
    private final static int MIN_IMG_SIZE = 200;

    private Context mContext;

    private View rootView;

    private TextView tvImgpath;
    private TextInputLayout tilTitle;
    private TextInputLayout tilContent;
    private TextView tvImgSize;
    private SeekBar sbImgSize;
    private TextView tvTxtSize;
    private SeekBar sbTxtSize;
    private TextView tvRgbDebug;
    private SeekBar sbRgbDebug;
    private ImageView ivDialogImg;


    private String mImgPath;

    public LaboratoryDialog(Context context) {
        mContext = context;
        createDialog(context);
    }


    @Override
    void createDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.dialog_laboratory, null);
        new AlertDialog.Builder(context)
                .setView(rootView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = getInputTitle();
                        String content = getInputContent();
                        String rgbDebug = sbRgbDebug.getProgress() + "";
                        String txtSize = sbTxtSize.getProgress() + MIN_TXT_SIZE + "";
                        String imgSize = sbImgSize.getProgress() + MIN_IMG_SIZE + "";
                        ImgHtmlAsyncTask imgHtmlAsyncTask = new ImgHtmlAsyncTask();
                        Log.i(TAG, "onClick: 确定");
                        Log.i(TAG, "onClick: rgb"+rgbDebug);
                        Log.i(TAG, "onClick: txtSize"+txtSize);
                        Log.i(TAG, "onClick: imgSize"+imgSize);
                        imgHtmlAsyncTask.execute(mImgPath, title, content, "0", txtSize, imgSize, rgbDebug);
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
        tvImgSize = (TextView) rootView.findViewById(R.id.tv_img_size);
        sbImgSize = (SeekBar) rootView.findViewById(R.id.sb_img_size);
        tvTxtSize = (TextView) rootView.findViewById(R.id.tv_txt_size);
        sbTxtSize = (SeekBar) rootView.findViewById(R.id.sb_txt_size);
        tvRgbDebug = (TextView) rootView.findViewById(R.id.tv_rgb_debug);
        sbRgbDebug = (SeekBar) rootView.findViewById(R.id.sb_rgb_debug);
        ivDialogImg = (ImageView) rootView.findViewById(R.id.iv_dialog_img);
    }

    @Override
    void initVar() {
        sbImgSize.setProgress(550);
        String initImgStr = mContext.getResources().getString(R.string.image_size) + 750;
        tvImgSize.setText(initImgStr);

        String initTxtStr = mContext.getResources().getString(R.string.txt_size) + 5;
        sbTxtSize.setProgress(4);
        tvTxtSize.setText(initTxtStr);

        String initRgbStr = mContext.getResources().getString(R.string.debug_img_color) + 0;
        //   sbRgbDebug.setProgress(0);
        tvRgbDebug.setText(initRgbStr);

    }

    @Override
    void initAction() {
        sbImgSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String str = mContext.getResources().getString(R.string.image_size) + (MIN_IMG_SIZE + i);
                tvImgSize.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbTxtSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String str = mContext.getResources().getString(R.string.txt_size) + (MIN_TXT_SIZE + i);
                tvTxtSize.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbRgbDebug.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String str = mContext.getResources().getString(R.string.debug_img_color) + i;
                tvRgbDebug.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
    public  void setTvImgpath(String imgpath) {
        tvImgpath.setText("图片路径:" + imgpath);
        mImgPath = imgpath;
    }
}