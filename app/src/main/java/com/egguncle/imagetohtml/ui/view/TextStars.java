package com.egguncle.imagetohtml.ui.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.egguncle.imagetohtml.model.json.ResultHtmlImage;
import com.egguncle.imagetohtml.util.network.NetUtil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * Created by egguncle on 17-5-9.
 * 一个自定义的viewgroup 用于展示textview
 * <p>
 * 在屏幕中分出一个3×3的矩阵，每个小方格的内随机位置出现一个textview
 */

public class TextStars extends RelativeLayout {

    private final static String TAG = "TextStars";

    private final static int DEFAULT_DISTANCE = 200;
    private final static int ANIMATION_TIME=1000;

    private Context mContext;
    //viewgroup高度
    private int height;
    //viewgroup宽度
    private int width;

    private int distanceX;
    private int distanceY;

    private float lastX;
    private float lastY;

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
       // Log.i(TAG, "onMeasure: " + width + " " + height);
        setMeasuredDimension(width, height);
        distanceX = width / 3;
        distanceY = height / 3;


    }


    /**
     * 重写reomveview使其在被删除之前有一个消失的动画
     *
     * @param start
     * @param count
     */
    @Override
    public void removeViews(int start, int count) {
        final View childView = this.getChildAt(0);
        AlphaAnimation animHide = new AlphaAnimation(1, 0);
        animHide.setDuration(ANIMATION_TIME);
        childView.startAnimation(animHide);
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {

            }
        };
        handler.postDelayed(r, 1000);


        super.removeViews(start, count);
    }

    public void removeViewWithOutAnim(View v) {
        super.removeView(v);
    }

    /**
     * 向布局中添加文字
     *
     * @param content
     */
    public void add(String content) {
        if (textViewList.size() > 9) {
//            this.removeViews(0, 1);
//            textViewList.remove(0);
        } else {
            int postion = textViewList.size();
            TStar textView = new TStar(mContext);
            textView.setPostion(postion);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);

            Random random = new Random();
            switch (postion) {
                case 1: {
                    params.leftMargin = random.nextInt(distanceX);
                    params.topMargin = random.nextInt(distanceY);
                }
                break;
                case 2: {
                    params.leftMargin = random.nextInt(distanceX)+distanceX;
                    params.topMargin = random.nextInt(distanceY);
                }
                break;
                case 3: {
                    params.leftMargin = random.nextInt(distanceX)+2*distanceX;
                    params.topMargin = random.nextInt(distanceY);
                }
                break;
                case 4: {
                    params.leftMargin = random.nextInt(distanceX);
                    params.topMargin = random.nextInt(distanceY)+distanceY;
                }
                break;
                case 5: {
                    params.leftMargin = random.nextInt(distanceX)+distanceX;
                    params.topMargin = random.nextInt(distanceY)+distanceY;
                }
                break;
                case 6: {
                    params.leftMargin = random.nextInt(distanceX)+2*distanceX;
                    params.topMargin = random.nextInt(distanceY)+distanceY;
                }
                break;
                case 7: {
                    params.leftMargin = random.nextInt(distanceX);
                    params.topMargin = random.nextInt(distanceY)+2*distanceY;
                }
                break;
                case 8: {
                    params.leftMargin = random.nextInt(distanceX)+distanceX;
                    params.topMargin = random.nextInt(distanceY)+2*distanceY;
                }
                break;
                case 9: {
                    params.leftMargin = random.nextInt(distanceX)+2*distanceX;
                    params.topMargin = random.nextInt(distanceY)+2*distanceY;
                }
                break;
                default:{
                    params.leftMargin = random.nextInt(width);
                    params.topMargin = random.nextInt(height);
                }
            }

            textView.setLayoutParams(params);
            textView.setMaxEms(7);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine();
            textView.setText(content);
            textView.setTextSize(20);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "onClick: ---------------------------------");
                }
            });
            AlphaAnimation animDisplay = new AlphaAnimation(0, 1);
            animDisplay.setDuration(ANIMATION_TIME);
            textView.startAnimation(animDisplay);


            textViewList.add(textView);
            //   rootLayout.addView(textView);
            this.addView(textView);
         //   Log.i(TAG, "add: " + textViewList.size());
        }

    }

    /**
     * 向布局中添加文字
     *
     * @param htmlImage
     */
    public void add(ResultHtmlImage htmlImage) {
        if (textViewList.size() > 9) {
//            this.removeViews(0, 1);
//            textViewList.remove(0);
        } else {
            int postion = textViewList.size();
            TStar textView = new TStar(mContext);
            textView.setPostion(postion);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);

            Random random = new Random();
            switch (postion) {
                case 1: {
                    params.leftMargin = random.nextInt(distanceX);
                    params.topMargin = random.nextInt(distanceY);
                }
                break;
                case 2: {
                    params.leftMargin = random.nextInt(distanceX)+distanceX;
                    params.topMargin = random.nextInt(distanceY);
                }
                break;
                case 3: {
                    params.leftMargin = random.nextInt(distanceX)+2*distanceX;
                    params.topMargin = random.nextInt(distanceY);
                }
                break;
                case 4: {
                    params.leftMargin = random.nextInt(distanceX);
                    params.topMargin = random.nextInt(distanceY)+distanceY;
                }
                break;
                case 5: {
                    params.leftMargin = random.nextInt(distanceX)+distanceX;
                    params.topMargin = random.nextInt(distanceY)+distanceY;
                }
                break;
                case 6: {
                    params.leftMargin = random.nextInt(distanceX)+2*distanceX;
                    params.topMargin = random.nextInt(distanceY)+distanceY;
                }
                break;
                case 7: {
                    params.leftMargin = random.nextInt(distanceX);
                    params.topMargin = random.nextInt(distanceY)+2*distanceY;
                }
                break;
                case 8: {
                    params.leftMargin = random.nextInt(distanceX)+distanceX;
                    params.topMargin = random.nextInt(distanceY)+2*distanceY;
                }
                break;
                case 9: {
                    params.leftMargin = random.nextInt(distanceX)+2*distanceX;
                    params.topMargin = random.nextInt(distanceY)+2*distanceY;
                }
                break;
                default:{
                    params.leftMargin = random.nextInt(width);
                    params.topMargin = random.nextInt(height);
                }
            }

            textView.setLayoutParams(params);
            textView.setMaxEms(7);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine();
            String content=htmlImage.getContent();
            textView.setText(content);
            textView.setTextSize(20);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "onClick: ---------------------------------");
                }
            });
            AlphaAnimation animDisplay = new AlphaAnimation(0, 1);
            animDisplay.setDuration(ANIMATION_TIME);
            textView.startAnimation(animDisplay);
            textViewList.add(textView);
            //   rootLayout.addView(textView);
            this.addView(textView);
            //   Log.i(TAG, "add: " + textViewList.size());
        }

    }


    /**
     * 重写触摸事件，在触摸时移动内部的textview
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
           //     Log.i(TAG, "onTouch: down");
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //响应触摸事件  同时移动所有的textview
             //   Log.i(TAG, "onTouch: move");
                float xChange = event.getX() - lastX;
                float yChange = event.getY() - lastY;

                moveAllText(xChange, yChange);

            //    Log.i(TAG, "onTouch: " + xChange + " " + yChange);
                lastX = event.getX();
                lastY = event.getY();


                break;
            case MotionEvent.ACTION_SCROLL: {
          //      Log.i(TAG, "onTouch: scroll");
            }
            break;
            case MotionEvent.ACTION_UP: {
          //      Log.i(TAG, "onTouch: up");
            }
            break;
        }

        return true;
    }


    /**
     * 判断对应textview是否被移动到了屏幕不可见的地方
     *
     * @param view
     * @return
     */
    private boolean inLayout(View view) {
        float viewX = view.getX();
        float viewY = view.getY();
      //  Log.i(TAG, "inLayout: " + viewX + " " + viewY);
        if (viewX < 0 - DEFAULT_DISTANCE || viewX > width + DEFAULT_DISTANCE || viewY < 0 - DEFAULT_DISTANCE || viewY > height + DEFAULT_DISTANCE) {
            return true;
        }
        return false;
    }

    /**
     * 响应触摸时间后向同一个方向移动所有的text
     *
     * @param x
     * @param y
     */
    private void moveAllText(float x, float y) {

        Iterator iterator = textViewList.iterator();
        while (iterator.hasNext()) {
            TextView text = (TextView) iterator.next();
            LayoutParams params = (LayoutParams) text.getLayoutParams();
            params.leftMargin += x;
            params.topMargin += y;
            text.setLayoutParams(params);
            //
            if (inLayout(text)) {
                Log.i(TAG, "this view has move out the viewgroup");
                //   textViewList.remove(text);
                iterator.remove();
                removeViewWithOutAnim(text);
            }
        }
        if (textViewList.size()<=4){
            NetUtil.getHtmlFromServer();
        }
//        for (TextView text:textViewList){
//            LayoutParams params= (LayoutParams) text.getLayoutParams();
//            params.leftMargin+=x;
//            params.topMargin+=y;
//            text.setLayoutParams(params);
//         //
//            if(inLayout(text)){
//                Log.i(TAG, "this view has move out the viewgroup");
//             //   textViewList.remove(text);
//                removeViewWithOutAnim(text);
//            }
//        }

    }

//    /**
//     * 重写触摸事件，在触摸时移动内部的textview
//     * @param view
//     * @param motionEvent
//     * @return
//     */
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//
//        switch (motionEvent.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "onTouch: down");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "onTouch: move");
//                break;
//            case MotionEvent.ACTION_SCROLL:{
//                Log.i(TAG, "onTouch: scrool");
//                int x= (int) motionEvent.getX();
//                int y= (int) motionEvent.getY();
//                Log.i(TAG, "onTouch: "+x+" "+y);
//            }break;
//            case MotionEvent.ACTION_UP:{
//                Log.i(TAG, "onTouch: up");
//            }break;
//        }
//        return false;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


}
