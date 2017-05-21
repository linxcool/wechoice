package com.linxcool.wechoice.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.widget.RadioGroup;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.ui.fragment.MineFragment;
import com.linxcool.wechoice.ui.fragment.ArticleFragment;
import com.linxcool.wechoice.ui.fragment.ImageFragment;
import com.linxcool.wechoice.ui.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @BindView(R.id.tabGroup)
    RadioGroup tabGroup;

    SparseArray tabIds;
    List<Fragment> tabFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        initTabFragments();
        initToolbar();
    }

    private void initTabFragments() {
        tabIds = new SparseArray();
        tabIds.put(R.id.tab0, 0);
        tabIds.put(R.id.tab1, 1);
        tabIds.put(R.id.tab2, 2);
        tabIds.put(R.id.tab3, 3);

        tabFragments = new ArrayList<>();
        tabFragments.add(new ArticleFragment());
        tabFragments.add(new ImageFragment());
        tabFragments.add(new VideoFragment());
        tabFragments.add(new MineFragment());

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment: tabFragments) {
            transaction.add(R.id.tabContainer, fragment);
            transaction.hide(fragment);
        }
        transaction.show(tabFragments.get(0));
        transaction.commit();

        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                showFrament(tabIds.indexOfKey(checkedId));
            }
        });
    }

    private void showFrament(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment: tabFragments) {
            transaction.hide(fragment);
        }
        transaction.show(tabFragments.get(index));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
