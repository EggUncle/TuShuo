package com.egguncle.imagetohtml.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Looper;
import android.util.Log;


/**
 * Created by egguncle on 17-4-3.
 * 图片处理类
 */
public class Image2Html {


    private final static String TAG = "Image2Html";

    private Image2Html() {

    }


    /**
     * 将图像分块，获取每一块的颜色,并转换成html代码
     *
     * @param filePath  文件路径
     * @param blockSize 分块的大小
     * @param title     标题
     * @param content   填充的文字内容
     * @return
     */
    public static String imageToHtml(String filePath, int blockSize, String title, String content) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new RuntimeException("this function should not run on main thread!");
        }
        //缩放图片
        Bitmap bitmap = zoomBitmap(filePath);
        //用于构造html字符串
        StringBuilder htmlStr = new StringBuilder();
        if (content.isEmpty() || "".equals(content.replace(" ", ""))) {
            content = "图说";
        }
        //将填充内容转换为字符串数组，便于论询填充
        char[] chars = content.toCharArray();
        Log.i(TAG, "imageToHtml: " + bitmap.getWidth() + " " + bitmap.getHeight());

        int n = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        for (int i = 0; i < height - 1; i += blockSize) {
            for (int j = 0; j < width - 1; j += blockSize) {
                //  Log.i(TAG, "imageToHtml: - "+i+"  "+j);
                int pixel = 0;
                //获取对应坐标的颜色信息
                pixel = bitmap.getPixel(j, i);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int alpha = Color.alpha(pixel);

                htmlStr.append("<font style=color:rgba(" + red + "," + green + "," + blue + "," + alpha + ") >" + chars[n++ % content.length()] + "</font>");

            }
            htmlStr.append("<br>\n\n");
        }

        //   getHtml(htmlStr,height,width,blockSize);

        return getHtml(htmlStr, title, blockSize);
    }

    /**
     * 缩放图片
     *
     * @param filePath
     * @return
     */
    private static Bitmap zoomBitmap(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        int height = opts.outHeight;
        int width = opts.outWidth;
        Log.i(TAG, "imageToHtml: " + width + " " + height);

        opts.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0
                || bitmap.isRecycled()) {
            throw new RuntimeException("bad bitmap");
        }
        bitmap = null;
        //缩放图片 750只是一个暂时设置好的数字，用来缩放图片
        //这里的缩放并不单指放大或者缩小，而是说修改图片的尺寸，使其在转化为html代码后的显示效果更好
        //如果图片太大，会导致生成的html文件过大，导致加载出现卡吨甚至直接失去响应。
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float i = Math.max(750 / (float) width, 750 / (float) height);
        height = (int) (height * i);
        width = (int) (width * i);
        matrix.postScale(i, i);
        opts.inJustDecodeBounds = false;
        opts.inScaled = false;

        bitmap = BitmapFactory.decodeFile(filePath, opts);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        Log.i(TAG, "imageToHtml: " + width + "  " + height);
        Log.i(TAG, "imageToHtml:--- " + bitmap.getWidth() + "  " + bitmap.getHeight());
        return bitmap;
    }

//    /**
//     * 计算采样率
//     *
//     * @return
//     */
//    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        if (reqHeight == 0 || reqWidth == 0) {
//            return 1;
//        }
//
//        int height = options.outHeight;
//        int width = options.outWidth;
//        Log.i(TAG, "calculateInSampleSize: " + height + " " + width);
//
//        int inSampleSize = 1;
//        if (height > reqHeight || width > reqWidth) {
//            int halfHeight = height / 2;
//            int halfWidth = width / 2;
//            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//        Log.i(TAG, "calculateInSampleSize: " + inSampleSize);
//        return inSampleSize;
//    }


    /**
     * 将生成的html文字与一个简单的html页面拼接
     *
     * @param strInput 输入的字符串
     * @param title    html的标题
     * @param size     文字的大小
     * @return
     */
    public static String getHtml(StringBuilder strInput, String title, int size) {

        return "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                //   "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"+
                "    <title>" + title + " </title>\n" +
                "    <style type=\"text/css\">\n" +
                "        body {\n" +
                "            margin: 0px; padding: 0px; line-height:100%; letter-spacing:0px;text-align: center; \n" +
                "            font-size: " + size + " px;\n" +
                "            background-color: #000000;\n" +
                "            font-family: monospace;\n" +
                "        }\n" +

                "div{" +
                "white-space:nowrap;\n" +

                //  "width:" + width + "px;\n" +
                //   "height:" + height + "px;\n" +
                "margin-left:auto;margin-right:auto;" +
                "}" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +

                "<div>" +
                strInput +
                "</div>\n" +
                "</body>\n" +
                "</html>";

    }

    //将RGB转换为16进制Hex
//    public static String toHex(int r, int g, int b) {
//        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
//                + toBrowserHexValue(b);
//    }
//
//    private static String toBrowserHexValue(int number) {
//        StringBuilder builder = new StringBuilder(
//                Integer.toHexString(number & 0xff));
//        while (builder.length() < 2) {
//            builder.append("0");
//        }
//        return builder.toString().toUpperCase();
//    }

}
