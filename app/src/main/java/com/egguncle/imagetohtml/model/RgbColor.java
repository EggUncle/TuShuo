/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-23 上午10:20
 *
 */

package com.egguncle.imagetohtml.model;

/**
 * Created by egguncle on 17-5-21.
 * rgb颜色使用的类
 */

public class RgbColor {
    private int red;
    private int green;
    private int blue;

    public RgbColor(int r,int g,int b){
        red=r;
        green=g;
        blue=b;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
