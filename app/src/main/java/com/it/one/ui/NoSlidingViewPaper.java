package com.it.one.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 去掉左右滑动的属性的ViewPaper
 */

public class NoSlidingViewPaper extends ViewPager {
    public NoSlidingViewPaper(Context context) {
        super(context);
    }

    public NoSlidingViewPaper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
