/*
 * Create by EggUncle on 17-5-27 上午10:47
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-5-26 下午3:22
 *
 */

package com.egguncle.imagetohtml.ui.adapter;

/**
 * Created by egguncle on 17-4-30.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.egguncle.imagetohtml.MyApplication;
import com.egguncle.imagetohtml.R;
import com.egguncle.imagetohtml.ui.fragment.FragmentHome;
import com.egguncle.imagetohtml.ui.fragment.FragmentOther;
import com.egguncle.imagetohtml.ui.fragment.FragmentStars;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final static int PAGE_COUNT=2;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentStars();
            case 1:
                 return new FragmentHome();
        }

       return null;
    }

    @Override
    public int getCount() {

        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return MyApplication.getContext().getString(R.string.star_ocean);
            case 1:
                return MyApplication.getContext().getString(R.string.my);
        }
        return null;
    }
}
