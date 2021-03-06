package com.linxcool.wechoice.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linxcool.andbase.ui.util.DisplayUtil;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.ImageContract;
import com.linxcool.wechoice.data.entity.ImageCategory;
import com.linxcool.wechoice.data.model.ImageModel;
import com.linxcool.wechoice.presenter.ImagePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageFragment extends BaseFragment<ImagePresenter, ImageModel> implements ImageContract.View {

    @BindView(R.id.imageSubTabs)
    TabLayout subTabs;
    @BindView(R.id.imageIvMore)
    ImageView ivMore;
    @BindView(R.id.imageSubPages)
    ViewPager subPages;

    PopupWindow popupWindow;
    CmnFragmentPagerAdapter pageAdapter;
    PobupAdapter popupAdapter;

    List<ImageCategory> categories = new ArrayList<>();

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_image;
    }

    @Override
    protected void initViews(LayoutInflater inflater, Bundle bundle) {
        subPages.setAdapter(pageAdapter = new CmnFragmentPagerAdapter(getFragmentManager()));
        subTabs.setupWithViewPager(subPages);
        subTabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        View popupView = inflater.inflate(R.layout.popup_article_categorys, null);
        RecyclerView recyclerView = (RecyclerView) popupView.findViewById(R.id.grid);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        recyclerView.setAdapter(popupAdapter = new PobupAdapter());

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return false;
            }
        });
        popupWindow = new PopupWindow(popupView,
                DisplayUtil.getScreenWidth(getActivity()),
                DisplayUtil.getScreenHeight(getActivity()));
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(0);
    }

    @Override
    public void showCategorys(List<ImageCategory> data) {
        categories.clear();
        categories.addAll(data);
        pageAdapter.notifyDataSetChanged();
        popupAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.imageIvMore)
    public void onClick() {
        popupWindow.showAsDropDown(subTabs, 0, -dp2px(38));
        popupAdapter.notifyDataSetChanged();
    }

    class CmnFragmentPagerAdapter extends FragmentPagerAdapter {

        public CmnFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageListFragment.newInstance(categories.get(position));
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).toString();
        }
    }

    class PobupAdapter extends RecyclerView.Adapter<PobupViewHolder> {

        @Override
        public PobupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.popup_article_categorys_item, parent, false);
            return new PobupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PobupViewHolder holder, int position) {
            holder.tvTitle.setText(categories.get(position).toString());
            int current = subPages.getCurrentItem();
            if (current == position) {
                holder.tvTitle.setTextColor(getColor(R.color.subtab_text_selected));
            } else {
                holder.tvTitle.setTextColor(getColor(R.color.subtab_text_default));
            }
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }
    }

    class PobupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        PobupViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            subPages.setCurrentItem(pos, true);
            popupWindow.dismiss();
        }
    }

}
