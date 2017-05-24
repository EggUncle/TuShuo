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
