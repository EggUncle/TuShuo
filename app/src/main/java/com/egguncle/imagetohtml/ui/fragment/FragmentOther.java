/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-23 上午10:20
 *
 */

package com.egguncle.imagetohtml.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egguncle.imagetohtml.R;

/**
 * Created by egguncle on 17-4-30.
 */

public class FragmentOther extends Fragment {

    private final static String TAG = "FragmentOther";
    private final static String CONTEXT="context";

    public FragmentOther() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other, container, false);
        initView();
        initVar();
        initAction();
        return rootView;
    }

    private void initView() {

    }

    private void initVar(){

    }
    private void initAction(){

    }
}
