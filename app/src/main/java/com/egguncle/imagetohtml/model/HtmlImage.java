/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-23 上午10:20
 *
 */

package com.egguncle.imagetohtml.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by egguncle on 17-4-30.
 * 转化为html代码的图片的实体的类
 */

public class HtmlImage extends DataSupport implements Serializable {

    //图片文件路径
    private String imgPath;
    //html文件路径
    private String htmlPath;
    //标题
    private String title;
    //填充的文字
    private String content;
    //html的名字 用于请求时获取对应页面
    private String htmlName;

    //html文件是否上传了 0 没上传 1上传了
    private int isUpLoad;

    //是否在实验性状态下生成
    private int isLaboratory;

    public int getIsLaboratory() {
        return isLaboratory;
    }

    public void setIsLaboratory(int isLaboratory) {
        this.isLaboratory = isLaboratory;
    }

    public int isUpLoad() {
        return isUpLoad;
    }

    public void setUpLoad(int upLoad) {
        isUpLoad = upLoad;
    }



    public String getHtmlName() {
        return htmlName;
    }

    public void setHtmlName(String htmlName) {
        this.htmlName = htmlName;
    }

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
