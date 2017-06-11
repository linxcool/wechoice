package com.linxcool.wechoice.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.data.CollectDataCache;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.util.WeChatTool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    ArticleItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initToolbar();
        initViews();
        start();
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.setBackgroundColor(0);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) progressBar.setVisibility(View.GONE);
                else progressBar.setVisibility(View.VISIBLE);
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void start() {
        WeChatTool.init(this);
        item = (ArticleItem) getIntent().getSerializableExtra("item");

        if (TextUtils.isEmpty(item.getUrl())) {
            finish();
        } else {
            webView.loadUrl(item.getUrl());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_more, menu);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}