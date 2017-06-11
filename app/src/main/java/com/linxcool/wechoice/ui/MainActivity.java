package com.linxcool.wechoice.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.widget.RadioGroup;

import com.igexin.sdk.PushManager;
import com.linxcool.andbase.util.LogUtil;
import com.linxcool.trdsdk.PushService;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseActivity;
import com.linxcool.wechoice.ui.fragment.ArticleFragment;
import com.linxcool.wechoice.ui.fragment.ImageFragment;
import com.linxcool.wechoice.ui.fragment.MineFragment;
import com.linxcool.wechoice.ui.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {

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
        initSdks();
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

    void initSdks() {
        LogUtil.e("start init third sdks");
        PushManager.getInstance().initialize(getApplicationContext(), null);
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), PushService.class);
        LogUtil.e("end init third sdks");
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
