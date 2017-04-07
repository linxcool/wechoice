package com.linxcool.wechoice.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.MainContract;
import com.linxcool.wechoice.data.entity.ArticleCategory;
import com.linxcool.wechoice.data.model.MainModel;
import com.linxcool.wechoice.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linxcool on 17/3/16.
 */

public class MainFragment extends BaseFragment<MainPresenter, MainModel> implements MainContract.View {

    @BindView(R.id.subTabs)
    TabLayout subTabs;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.subPages)
    ViewPager subPages;

    CmnFragmentPagerAdapter adapter;

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews(LayoutInflater inflater, Bundle bundle) {
        subPages.setAdapter(adapter = new CmnFragmentPagerAdapter(getFragmentManager()));
        subTabs.setupWithViewPager(subPages);
        subTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void showCategorys(List<ArticleCategory> categories) {
        adapter.refresh(categories);
    }

    @OnClick(R.id.ivMore)
    public void onClick() {
    }

    class CmnFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<ArticleCategory> categories = new ArrayList<>();

        public void refresh(List<ArticleCategory> data) {
            if (data != null) {
                categories.clear();
                categories.addAll(data);
                notifyDataSetChanged();
            }
        }

        public CmnFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainArticlesFragment.newInstance(categories.get(position));
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
