package com.linxcool.wechoice.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by linxcool on 16/8/29.
 */
public class LViewPager extends ViewPager {

    private boolean scrollable;

    public LViewPager(Context context) {
        super(context);
    }

    public LViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean isCanScroll) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!scrollable)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!scrollable)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

}
