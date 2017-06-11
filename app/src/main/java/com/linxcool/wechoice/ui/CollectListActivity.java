package com.linxcool.wechoice.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.data.CollectDataCache;
import com.linxcool.wechoice.ui.fragment.ArticleListFragment;
import com.linxcool.wechoice.ui.fragment.ImageListFragment;

import butterknife.ButterKnife;

/**
 * Created by linxcool on 17/5/28.
 */

public class CollectListActivity extends BaseActivity {

    public static String KEY_TYPE = "type";
    public static final int TYPE_ARTICLE = 0;
    public static final int TYPE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_list);
        ButterKnife.bind(this);
        initToolbar();
        initViews();
    }

    void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = null;
        int type = getIntent().getIntExtra(KEY_TYPE, TYPE_ARTICLE);
        if (type == TYPE_ARTICLE) {
            fragment = ArticleListFragment.newInstance(CollectDataCache.asArticleCollectCategory());
        } else {
            fragment = ImageListFragment.newInstance(CollectDataCache.asImageCollectCategory());
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();
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
