package com.linxcool.wechoice.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
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
        String url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            finish();
        } else {
            webView.loadUrl(url);
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}