package com.egguncle.imagetohtml.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.egguncle.imagetohtml.model.HtmlImage;


/**
 * Created by egguncle on 17-5-23.
 * 显示在textstarts中专用的text
 */

public class TStar extends android.support.v7.widget.AppCompatTextView{
    //是否可见
    private boolean isVisabale;
    //编号 用于判断其应该在3×3的矩形里的哪个位置出现
    private int postion;


    private HtmlImage htmlImage;

    public boolean isVisabale() {
        return isVisabale;
    }

    public void setVisabale(boolean visabale) {
        isVisabale = visabale;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public HtmlImage getHtmlImage() {
        return htmlImage;
    }

    public void setHtmlImage(HtmlImage htmlImage) {
        this.htmlImage = htmlImage;
    }


    public TStar(Context context) {
        this(context,null);

    }

    public TStar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TStar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
