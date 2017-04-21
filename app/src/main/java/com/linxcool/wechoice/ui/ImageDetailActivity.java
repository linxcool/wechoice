package com.linxcool.wechoice.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.linxcool.andbase.ui.util.DisplayUtil;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.ui.widget.PullBackLayout;
import com.linxcool.wechoice.ui.widget.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity implements PullBackLayout.Callback {


    @BindView(R.id.photoViw)
    PhotoView photoTouchIv;
    @BindView(R.id.pullBackLayout)
    PullBackLayout pullBackLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ColorDrawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        initToolbar();
        initView();
    }

    public void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pullBackLayout.setCallback(this);

        background = new ColorDrawable(Color.BLACK);
        DisplayUtil.getRootView(this).setBackgroundDrawable(background);

        ImageItem item = (ImageItem) getIntent().getSerializableExtra("item");
        Glide.with(this).load(item.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.jc_error_normal)
                .crossFade()
                .into(photoTouchIv);

        setTitle(item.getTitle());

        toolBarFadeIn();
    }

    @Override
    public void onPullStart() {
        toolBarFadeOut();
    }

    private void toolBarFadeIn() {
        toolbar.animate()
                .alpha(1.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
    }

    private void toolBarFadeOut() {
        toolbar.animate()
                .alpha(0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        background.setAlpha((int) (0xff * (1f - progress)));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
