package com.linxcool.wechoice.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.jcodecraeer.xrecyclerview.LoadingMoreFooter;
import com.linxcool.andbase.ui.util.DisplayUtil;

import java.lang.reflect.Field;

/**
 * Created by linxcool on 17/4/5.
 */

public class XRecyclerView extends com.jcodecraeer.xrecyclerview.XRecyclerView {

    LoadingListener listener;

    public XRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    void initView(Context context) {
        int padding = DisplayUtil.dp2px(context, 12);
        View mFootView = getFieldValue("mFootView");
        mFootView.setPadding(0, padding, 0, padding);
    }

    @Override
    public void setLoadingListener(LoadingListener listener) {
        super.setLoadingListener(listener);
        this.listener = listener;
    }


    public void load() {
        setLoadingFlag(true);
        View mFootView = getFieldValue("mFootView");
        if (mFootView instanceof LoadingMoreFooter) {
            ((LoadingMoreFooter) mFootView).setState(LoadingMoreFooter.STATE_LOADING);
        } else {
            mFootView.setVisibility(View.VISIBLE);
        }
        if (listener != null) {
            listener.onLoadMore();
        }
    }

    private void setLoadingFlag(boolean flag) {
        try {
            Field field = getField("isLoadingData");
            field.setBoolean(this, flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> T getFieldValue(String name) {
        try {
            Field field = getField(name);
            field.setAccessible(true);
            return (T) field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Field getField(String name) throws NoSuchFieldException {
        Class<?> sup = com.jcodecraeer.xrecyclerview.XRecyclerView.class;
        Field field = sup.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

}
