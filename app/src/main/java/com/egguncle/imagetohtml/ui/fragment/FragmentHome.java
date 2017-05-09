package com.egguncle.imagetohtml.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * <p>
 * 主页的fragment
 */

public class FragmentHome extends Fragment {

    private final static String TAG = "FragmentHome";
    private final static String CONTEXT = "context";

    private View rootView;
    private RecyclerView rcvHome;
    private HomeRcvAdapter homeRcvAdapter;
    private List<HtmlImage> listData;

    //广播相关
    private FragmentHome.HomeReceiver homeReceiver;
    public static LocalBroadcastManager localBroadcastManager;
    public final static String HOME_BROADCAST = "com.egguncle.imagetohtml.HOME_BOROADCAST";


    public FragmentHome() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //广播相关
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HOME_BROADCAST);
        homeReceiver = new FragmentHome.HomeReceiver();
        localBroadcastManager.registerReceiver(homeReceiver, intentFilter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initVar();
        initAction();
        return rootView;
    }

    private void initView() {
        rcvHome = (RecyclerView) rootView.findViewById(R.id.rcv_home);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
      //  linearLayoutManager.setReverseLayout(true);
        rcvHome.setLayoutManager(linearLayoutManager);

        rcvHome.hasFixedSize();
    }

    private void initVar() {
        listData = new ArrayList<>();
        listData.addAll(DataSupport.findAll(HtmlImage.class));
//        if (listData.size()!=0){
//            rcvHome.smoothScrollToPosition(listData.size()-1);
//        }
//        for (HtmlImage h : listData) {
//            Log.i(TAG, "initVar: " + h.getImgPath() + " " + h.getHtmlPath() + " " + h.getContent() + " " + h.getTitle());
//        }

        homeRcvAdapter = new HomeRcvAdapter(listData);
        rcvHome.setAdapter(homeRcvAdapter);
    }

    private void initAction() {

    }


    private class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra("type");
            switch (type) {
                case "add_item": {
                    HtmlImage htmlImage = (HtmlImage) intent.getBundleExtra("data").get("htmlImg");
                    homeRcvAdapter.insertItem(htmlImage);
                    rcvHome.smoothScrollToPosition(listData.size()-1);
                }
                break;
                case "save_file_finish": {
                    homeRcvAdapter.refreshLastItem();
                }
                break;
            }


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(homeReceiver);
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        return localBroadcastManager;
    }
}
