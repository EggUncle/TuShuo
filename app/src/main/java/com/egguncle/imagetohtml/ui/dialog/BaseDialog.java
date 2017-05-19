package com.egguncle.imagetohtml.ui.dialog;

import android.content.Context;

/**
 * Created by egguncle on 17-5-20.
 */

public abstract  class BaseDialog {

    abstract void createDialog(Context context);
    abstract void initView();
    abstract void initVar();
    abstract void initAction();

}
