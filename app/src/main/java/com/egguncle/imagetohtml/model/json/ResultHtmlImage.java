package com.egguncle.imagetohtml.model.json;

import java.io.Serializable;

/**
 * Created by egguncle on 17-5-24.
 */

public class ResultHtmlImage implements Serializable{
    private int id;

    private String content;

    private String title;

    private String htmlPath;

    private String uploadDate;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getHtmlPath() {
        return this.htmlPath;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadDate() {
        return this.uploadDate;
    }
}

