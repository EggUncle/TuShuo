# 图说
图说，一个将图片转化为文字矩阵的Android APP

下载链接  http://www.coolapk.com/apk/com.egguncle.imagetohtml

![](https://github.com/EggUncle/Demo/blob/master/markdownimg/Screenshot_20170504-000628.png?raw=true) 

## 基本功能：
选择图片，输入标题和填充文字，生成对应的html文件

![主界面](https://github.com/EggUncle/Demo/blob/master/markdownimg/Screenshot_20170503-005042.png?raw=true  "主界面")

![图片选择](https://github.com/EggUncle/Demo/blob/master/markdownimg/Screenshot_20170503-010912.png?raw=true  "图片选择")


![原图](https://github.com/EggUncle/Demo/blob/master/markdownimg/845046073.jpg?raw=true  "原图")

![生成效果](https://github.com/EggUncle/Demo/blob/master/markdownimg/Screenshot_20170503-005054.png?raw=true  "生成效果")



## 生成过程
```java
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
```
整个过程还是很简单的，直接读取一些坐标信息，获取颜色值，转化为html代码。

PS：进行这一步之前先对图片进行了缩放，使图片有一个相对好处理的尺寸，使最后生成的html页面不至于太大导致webview在加载时出现ANR，至于到底图片改缩放多少，我自己也还在摸索，如果你有更好的图片尺寸或者对于html生成加载有什么更好的建议，请给我留言。

## 更新
2017-5-5
增加将webview内容保存为图片文件的功能

2017-5-22
增加将定制化页面效果的功能，可以设置文字大小，生成的页面尺寸，背景颜色，亮度参数。
