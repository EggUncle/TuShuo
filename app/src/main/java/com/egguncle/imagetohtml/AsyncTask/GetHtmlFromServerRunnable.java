/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-24 下午2:36
 *
 */

package com.egguncle.imagetohtml.AsyncTask;

import com.egguncle.imagetohtml.util.network.NetWorkFunc;

/**
 * Created by egguncle on 17-5-24.
 */

public class GetHtmlFromServerRunnable implements Runnable{
    public void run(){
        NetWorkFunc.getHtmlFromServer();
    }
}
