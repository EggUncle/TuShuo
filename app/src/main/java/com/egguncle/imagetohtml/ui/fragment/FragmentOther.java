package com.egguncle.imagetohtml.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.model.HtmlImage;
import com.egguncle.imagetohtml.ui.adapter.HomeRcvAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egguncle on 17-4-30.
 */

public class FragmentOther extends Fragment {

    private final static String TAG = "FragmentOther";
    private final static String CONTEXT="context";

    private View rootView;

    public FragmentOther() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_other, container, false);
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
