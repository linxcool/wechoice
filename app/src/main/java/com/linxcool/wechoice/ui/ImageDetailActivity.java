package com.linxcool.wechoice.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.linxcool.andbase.ui.util.StatusBarCompat;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.ui.widget.PullBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * des:大图详情
 * Created by xsf
 * on 2016.09.14:35
 */
public class ImageDetailActivity extends BaseActivity implements PullBackLayout.Callback {


    @BindView(R.id.photo_touch_iv)
    ImageView photoTouchIv;
    @BindView(R.id.pull_back_layout)
    PullBackLayout pullBackLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        initToolbar();
        initView();
    }

    public void initView() {
        pullBackLayout.setCallback(this);
        toolBarFadeIn();
        loadPhotoIv();
    }

    private void loadPhotoIv() {
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.jc_error_normal)
                .crossFade()
                .into(photoTouchIv);
    }


    protected void hideOrShowToolbar() {
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
        } else {
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();
        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    private void toolBarFadeOut() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        //mBackground.setAlpha((int) (0xff * (1f - progress)));
    }

    @Override
    public void onPullCancel() {
        toolBarFadeIn();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

}
