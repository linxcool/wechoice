package com.linxcool.wechoice.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.linxcool.andbase.ui.swipeback.SwipeBackActivity;
import com.linxcool.andbase.ui.util.ToastUtil;
import com.linxcool.wechoice.R;

public class BaseActivity extends SwipeBackActivity implements Toolbar.OnMenuItemClickListener  {

    protected ProgressDialog progressDialog;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            setTitle(getString(R.string.app_name));
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            toolbar.setOnMenuItemClickListener(this);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }

    public void showToastMessage(String msg) {
        ToastUtil.showInUiThread(this, msg);
    }

    public boolean isShowing() {
        return !isFinishing();
    }

    public void closeSelf() {
        finish();
    }

    public void showProgress(boolean active) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (active) {
            progressDialog = ProgressDialog.show(this, null, "加载中...");
        }
    }

}
