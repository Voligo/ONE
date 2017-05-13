package com.it.one.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import com.it.one.R;

/**
 * Created by fenglichao on 17/5/11.
 */

public class TestFragment extends BaseFragment {
    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        ScrollView mScrollView = (ScrollView) view.findViewById(R.id.sv_test);
        mScrollView.smoothScrollTo(0, 0);
        return view;
    }
}
