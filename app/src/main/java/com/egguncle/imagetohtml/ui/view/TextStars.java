package com.egguncle.imagetohtml.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egguncle.imagetohtml.R;

import java.util.ArrayList;
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
//    private View rootView;
//    private RelativeLayout rootLayout;

    //布局中文字的集合
    private List<String> data;

    public TextStars(Context context) {
        this(context, null);
    }

    public TextStars(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextStars(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        rootView = LayoutInflater.from(mContext).inflate(R.layout.view_text_stars, this);
//        rootLayout = (RelativeLayout) rootView.findViewById(R.id.txt_starts_view);

        data = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.i(TAG, "onLayout: ");
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                LayoutParams lParams = (LayoutParams) childView.getLayoutParams();
//                childView.layout(lParams.leftMargin, lParams.topMargin, lParams.leftMargin + childWidth,
//                        lParams.topMargin + childHeight);

                Random random = new Random();
                int x = random.nextInt(width - childWidth);
                int y = random.nextInt(height - childHeight);

                childView.layout(x, y, x + childWidth, y + childHeight);
                Log.i(TAG, "onLayout: params"+lParams.leftMargin+" "+lParams.topMargin);
                Log.i(TAG, "onLayout: " + i + " " + childWidth + " " + childHeight);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        Log.i(TAG, "onMeasure: " + " " + width + " " + height);

        Log.i(TAG, "onMeasure: " + widthMode + " " + heightMode);
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = getChildAt(i);
//            if (childView.getVisibility() != View.GONE) {
//
//                int childWidth = childView.getMeasuredWidth();
//                int childHeight = childView.getMeasuredHeight();
//                Log.i(TAG, "onMeasure: "+childWidth+" "+childHeight);
////                Random random = new Random();
////                lParams.leftMargin=random.nextInt(width - childWidth)+childWidth;
////                lParams.topMargin= random.nextInt(height - childHeight);
////                lParams.rightMargin=random.nextInt(width - childWidth);
////                lParams.bottomMargin= random.nextInt(height - childHeight);
////                lParams.leftMargin = 50;
////                lParams.topMargin = 50;
//              //  measureChild(childView, widthMeasureSpec, heightMeasureSpec);
//            }
//        }


        setMeasuredDimension(width, height);
    }

//    @Override
//    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return new MarginLayoutParams(getContext(), attrs);
//    }

    /**
     * 向布局中添加文字
     *
     * @param content
     */
    public void add(String content) {
        data.add(content);
        TextView textView = new TextView(mContext);
        TextStars.LayoutParams params = new TextStars.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        textView.setLayoutParams(params);
        textView.setText(content);
        textView.setTextColor(Color.RED);

        //   rootLayout.addView(textView);
        this.addView(textView);


    }

}
