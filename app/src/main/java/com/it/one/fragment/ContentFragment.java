package com.it.one.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.it.one.R;


/**
 *
 */

public class ContentFragment extends BaseFragment {

    protected ViewPager vpInfo;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.content_base, null);
        vpInfo = (ViewPager) view.findViewById(R.id.vp_detail);
        return view;
    }
}
