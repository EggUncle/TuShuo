package com.egguncle.imagetohtml.ui.adapter;

/**
 * Created by egguncle on 17-4-30.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.egguncle.imagetohtml.ui.fragment.FragmentHome;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final static int PAGE_COUNT=1;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentHome();
//            case 1:
//                return new FragmentOther();
        }

       return null;
    }

    @Override
    public int getCount() {

        return PAGE_COUNT;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return MyApplication.getContext().getString(R.string.my);
//            case 1:
//                return MyApplication.getContext().getString(R.string.star_ocean);
//        }
//        return null;
//    }
}
