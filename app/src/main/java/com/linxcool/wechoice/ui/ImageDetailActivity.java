package com.linxcool.wechoice.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.linxcool.andbase.ui.util.DisplayUtil;
import com.linxcool.andbase.ui.util.ToastUtil;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.data.CollectDataCache;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.ui.widget.PullBackLayout;
import com.linxcool.wechoice.ui.widget.photoview.PhotoView;
import com.linxcool.wechoice.util.WeChatTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity implements Handler.Callback, PullBackLayout.Callback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pullBackLayout)
    PullBackLayout pullBackLayout;
    @BindView(R.id.photoViw)
    PhotoView photoView;
    @BindView(R.id.thumbViw)
    ImageView thumbViw;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Handler handler;
    ImageItem item;
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
        handler = new Handler(this);
        item = (ImageItem) getIntent().getSerializableExtra("item");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pullBackLayout.setCallback(this);

        background = new ColorDrawable(Color.BLACK);
        DisplayUtil.getRootView(this).setBackgroundDrawable(background);

        Glide.with(this).load(item.getThumbLargeUrl()).into(thumbViw);

        setTitle(item.getTitle());

        toolBarFadeIn();

        handler.sendEmptyMessageDelayed(0, 100);
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
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (isFinishing()) {
            return false;
        }

        Glide.with(this).load(item.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(target);
        return false;
    }

    SimpleTarget target = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            photoView.setImageDrawable(resource);
            progressBar.setVisibility(View.GONE);
            thumbViw.setVisibility(View.GONE);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            progressBar.setVisibility(View.GONE);
            ToastUtil.showInUiThread(ImageDetailActivity.this, "图片加载失败");
            if(e != null) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
        menu.getItem(0).setVisible(false);
        if (CollectDataCache.contains(item)) {
            menu.getItem(1).setTitle(R.string.action_collect_remove);
        } else {
            menu.getItem(1).setTitle(R.string.action_collect);
        }
        return true;
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

    @Override
    public boolean onMenuItemClick(MenuItem menu) {
        if (menu.getItemId() == R.id.action_share) {
            String url = getIntent().getStringExtra("url");
            WeChatTool.shareWeb(url);
        } else if (menu.getItemId() == R.id.action_collect) {
            if (CollectDataCache.contains(item)) {
                CollectDataCache.remove(item);
                showToastMessage("取消收藏成功");
            } else {
                CollectDataCache.save(item);
                showToastMessage("加入收藏成功");
            }
            invalidateOptionsMenu();
        }
        return true;
    }
}
