package com.linxcool.wechoice.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.andbase.ui.util.DisplayUtil;
import com.linxcool.andbase.ui.util.ToastUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;


/**
 * Created by huchanghai on 2016/8/25.
 */
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {

    public T presenter;
    public E model;

    private ProgressDialog progressDialog;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(getActivity()), container, false);
        }
        ButterKnife.bind(this, rootView);
        initViews(inflater, savedInstanceState);
        initializeMvp(this);
        onInitComplete();
        return rootView;
    }

    /**
     * 获得布局ID
     *
     * @return
     */
    protected abstract int getLayout(Activity activity);

    /**
     * 初始化布局
     */
    protected abstract void initViews(LayoutInflater inflater, Bundle bundle);

    /**
     * 初始化MVP，要求presenter的构造器参数为(? extends BaseView, ? extends BaseModel)
     *
     * @param view
     */
    protected void initializeMvp(Object view) {
        try {
            ParameterizedType clsArgTypes = (ParameterizedType) view.getClass().getGenericSuperclass();
            Class<E> modelCls = (Class<E>) clsArgTypes.getActualTypeArguments()[1];
            model = modelCls.newInstance();

            Class<T> presenterCls = (Class<T>) clsArgTypes.getActualTypeArguments()[0];
            Constructor<T> constructor = (Constructor<T>) presenterCls.getConstructors()[0];
            constructor.setAccessible(true);
            presenter = constructor.newInstance(view, model);

            presenter.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 完成视图及MVP的初始化
     */
    protected void onInitComplete() {
        // Empty
    }

    public void setPresenter(BaseView presenter) {
        // Useless
    }

    public void showToastMessage(String msg) {
        ToastUtil.showInUiThread(getActivity(), msg);
    }

    public boolean isShowing() {
        return true;
    }

    public void closeSelf() {
        getActivity().finish();
    }

    public void showProgress(boolean active) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (active) {
            progressDialog = ProgressDialog.show(getActivity(), null, "加载中...");
        }
    }

    protected int dp2px(int dp) {
        return DisplayUtil.dp2px(getActivity(), dp);
    }

    protected int getColor(int resId) {
        return getResources().getColor(resId);
    }

    protected int getDimension(int resId) {
        return (int) getResources().getDimension(resId);
    }
}
