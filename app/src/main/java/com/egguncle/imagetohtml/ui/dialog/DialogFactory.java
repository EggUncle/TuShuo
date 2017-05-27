/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-23 上午10:20
 *
 */

package com.egguncle.imagetohtml.ui.dialog;

import android.content.Context;

/**
 * Created by egguncle on 17-5-20.
 * 创建弹窗的工厂类
 */

public class DialogFactory {

    private final static String TAG="DialogFactory";

    /**
     * 根据是否出于实验性模式来返回对应的操作对话框
     * @param context
     * @param isLaboratory
     * @return
     */
    public static BaseDialog getDialog(Context context,Boolean isLaboratory){
        if (isLaboratory){
            return new LaboratoryDialog(context);
        }else{
            return new HomeDialog(context);
        }
    }

}
