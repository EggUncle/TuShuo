package com.egguncle.imagetohtml.ui.dialog;

import android.content.Context;


/**
 * Created by egguncle on 17-5-20.
 */

public abstract class BaseDialog {

    abstract void createDialog(Context context);

    abstract void initView();

    abstract void initVar();

    abstract void initAction();

    /**
     * 获取输入的标题
     *
     * @return
     */
    abstract String getInputTitle();

    /**
     * 获取输入的填充文字
     *
     * @return
     */
    abstract String getInputContent();

    /**
     * 设置获取到的图片
     *
     * @param imgPath
     */
    public abstract void setIvDialogImg(String imgPath);

    /**
     * 显示图片路径
     *
     * @param imgpath
     */
    public abstract void setTvImgpath(String imgpath);
}
