package com.egguncle.imagetohtml.ui.dialog;

import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.ui.fragment.FragmentHome;
import com.egguncle.imagetohtml.util.FileUtil;
import com.egguncle.imagetohtml.util.NetUtil;

/**
 * Created by egguncle on 17-5-1.
 * home页面点击设置图片以后弹出的对话框
 */

public class HomeDialog {

    private final static String TAG = "HomeDialog";

    private Context mContext;

    private View rootView;
    private Dialog dialog;

    private TextView tvImgpath;
    private TextInputLayout tilTitle;
    private TextInputLayout tilContent;
    private ImageView ivDialogImg;
    private String mImgPath;


    public HomeDialog(Context context) {
        mContext = context;
        createDialog(context);
    }

    private void createDialog(final Context context) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.dialog_home, null);
        new AlertDialog.Builder(context)
                .setView(rootView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = getInputTitle();
                        String content = getInputContent();
                        String htmlPath = FileUtil.saveFile(mImgPath, title, content);
                        String htmlName = htmlPath.substring(htmlPath.lastIndexOf('/')+1, htmlPath.lastIndexOf('.'));
                        Log.i(TAG, "onClick: "+htmlName);
                        //将相关信息存入数据库中
                        HtmlImage htmlImage = new HtmlImage();
                        htmlImage.setImgPath(mImgPath);
                        htmlImage.setTitle(title);
                        htmlImage.setContent(content);
                        htmlImage.setHtmlPath(htmlPath);
                        htmlImage.setHtmlName(htmlName);


                        if (htmlPath != null) {
                            htmlImage.save();
                        }


                        //将htmlImg实例包装到bundle中，使用广播发送出去
                        Intent intent = new Intent(FragmentHome.HOME_BROADCAST);
                        intent.putExtra("type", "add_item");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("htmlImg", htmlImage);
                        intent.putExtra("data", bundle);


                        FragmentHome.getLocalBroadcastManager().sendBroadcast(intent);

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


    private void initView() {
        tvImgpath = (TextView) rootView.findViewById(R.id.tv_imgpath);
        tilTitle = (TextInputLayout) rootView.findViewById(R.id.til_title);
        tilContent = (TextInputLayout) rootView.findViewById(R.id.til_content);
        ivDialogImg = (ImageView) rootView.findViewById(R.id.iv_dialog_img);

    }

    private void initVar() {

    }

    private void initAction() {

    }

    /**
     * 获取输入的标题
     *
     * @return
     */
    private String getInputTitle() {
        return tilTitle.getEditText().getText().toString();
    }

    /**
     * 获取输入的填充文字
     *
     * @return
     */
    private String getInputContent() {
        return tilContent.getEditText().getText().toString();
    }

    /**
     * 设置获取到的图片
     *
     * @param imgPath
     */
    public void setIvDialogImg(String imgPath) {
        Glide.with(mContext).load(imgPath).into(ivDialogImg);
    }

    /**
     * 显示图片路径
     *
     * @param imgpath
     */
    public void setTvImgpath(String imgpath) {
        tvImgpath.setText("图片路径:" + imgpath);
        mImgPath = imgpath;
    }
}
