package com.egguncle.imagetohtml.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by egguncle on 17-4-30.
 * 转化为html代码的图片的实体的类
 */

public class HtmlImage extends DataSupport implements Serializable{

    //图片文件路径
    private String imgPath;
    //html文件路径
    private String htmlPath;
    //标题
    private String title;
    //填充的文字
    private String content;


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
