package com.linxcool.wechoice.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.VideoContract;
import com.linxcool.wechoice.data.entity.VideoCategory;
import com.linxcool.wechoice.data.model.VideoModel;
import com.linxcool.wechoice.presenter.VideoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by linxcool on 17/3/16.
 */

public class VideoFragment extends BaseFragment<VideoPresenter, VideoModel> implements VideoContract.View {

    @BindView(R.id.videoSubTabs)
    TabLayout subTabs;
    @BindView(R.id.videoSubPages)
    ViewPager subPages;

    CmnFragmentPagerAdapter pageAdapter;
    List<VideoCategory> categories = new ArrayList<>();

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_video;
    }

    @Override
    protected void initViews(LayoutInflater inflater, Bundle bundle) {
        subPages.setAdapter(pageAdapter = new CmnFragmentPagerAdapter(getFragmentManager()));
        subTabs.setupWithViewPager(subPages);
    }

    @Override
    public void showCategorys(List<VideoCategory> data) {
        categories.clear();
        categories.addAll(data);
        pageAdapter.notifyDataSetChanged();
    }

    class CmnFragmentPagerAdapter extends FragmentPagerAdapter {

        public CmnFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return VideoListFragment.newInstance(categories.get(position));
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).getName();
        }
    }
}
