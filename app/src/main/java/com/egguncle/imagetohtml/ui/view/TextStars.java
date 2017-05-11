package com.egguncle.imagetohtml.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egguncle.imagetohtml.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by egguncle on 17-5-9.
 * 星海页面内容，类似弹幕的展示一些textview
 */

public class TextStars extends RelativeLayout {

    private final static String TAG = "TextStars";
    private Context mContext;
    //viewgroup高度
    private int height;
    //viewgroup宽度
    private int width;



    //布局中textview的集合
    private List<TextView> textViewList;

    public TextStars(Context context) {
        this(context, null);
    }

    public TextStars(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextStars(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;


        textViewList = new LinkedList<>();



    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                LayoutParams lParams = (LayoutParams) childView.getLayoutParams();
                childView.layout(lParams.leftMargin, lParams.topMargin, lParams.leftMargin + childWidth,
                        lParams.topMargin + childHeight);

            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, height);
    }


    /**
     * 重写reomveview使其在被删除之前有一个消失的动画
     * @param start
     * @param count
     */
    @Override
    public void removeViews(int start, int count) {
        final View childView=this.getChildAt(0);
        AlphaAnimation  animHide=new AlphaAnimation(1,0);
        animHide.setDuration(2000);
        childView.startAnimation(animHide);
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run() {

            }
        };
        handler.postDelayed(r,2000);


        super.removeViews(start, count);
    }

    /**
     * 向布局中添加文字
     *
     * @param content
     */
    public void add(String content) {

        TextView textView = new TextView(mContext);
        TextStars.LayoutParams params = new TextStars.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        Random random = new Random();
        params.leftMargin = random.nextInt(width - 100);
        params.topMargin = random.nextInt(height - 100);
        textView.setLayoutParams(params);
        textView.setMaxEms(7);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setSingleLine();
        textView.setText(content);
        textView.setTextSize(20);
        AlphaAnimation  animDisplay=new AlphaAnimation(0,1);
        animDisplay.setDuration(2000);
        textView.startAnimation(animDisplay);

        if (textViewList.size()>=10){
            this.removeViews(0,1);
            textViewList.remove(0);

        }
        textViewList.add(textView);
        //   rootLayout.addView(textView);
        this.addView(textView);
        Log.i(TAG, "add: "+textViewList.size());


    }

}
