package com.it.one.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.it.one.fragment.HomeContent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mHomeFragmentList = new ArrayList<>();
    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mHomeFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mHomeFragmentList.size();
    }

    public void addFragment(HomeContent homeContent) {
        mHomeFragmentList.add(homeContent);
    }

}
