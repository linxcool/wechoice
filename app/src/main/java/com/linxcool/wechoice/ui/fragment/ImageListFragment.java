package com.linxcool.wechoice.ui.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.linxcool.andbase.ui.util.DisplayUtil;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.entity.ImageCategory;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.data.model.ImageListModel;
import com.linxcool.wechoice.presenter.ImageListPresenter;
import com.linxcool.wechoice.ui.widget.SpacesItemDecoration;
import com.linxcool.wechoice.ui.widget.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;

public class ImageListFragment extends BaseFragment<ImageListPresenter, ImageListModel>
        implements ImageListContract.View, XRecyclerView.LoadingListener {

    @BindView(list)
    XRecyclerView recyclerView;

    ImageAdapter adapter;
    List<ImageItem> data = new ArrayList<>();
    boolean isRefreshData = false;
    boolean isLoadingData = false;
    int currentPage;
    int imgWidth;

    public static ImageListFragment newInstance(ImageCategory category) {
        Bundle args = new Bundle();
        args.putString("cid", category.getCid());
        ImageListFragment fragment = new ImageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_image_list;
    }

    @Override
    protected void initViews(LayoutInflater inflater, Bundle bundle) {
        int padding = getDimension(R.dimen.def_padding) / 2;
        imgWidth = (DisplayUtil.getScreenWidth(getActivity()) - 6 * padding) / 2;
        recyclerView.setPadding(padding, 0, padding, 0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new ImageAdapter());
        recyclerView.addItemDecoration(new SpacesItemDecoration(padding));
        recyclerView.setLoadingListener(this);
        recyclerView.setRefreshProgressStyle(ProgressStyle.SquareSpin);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
    }

    @Override
    protected void onInitComplete() {
        if(data.isEmpty()) {
            recyclerView.refresh();
        }
    }

    @Override
    public String getCategoryId() {
        return getArguments().getString("cid");
    }

    @Override
    public List<ImageItem> getImages() {
        return data;
    }

    @Override
    public void showImages(List<ImageItem> list) {
        if (isRefreshData) {
            currentPage = 0;
            data.clear();
            data.addAll(0, list);
            recyclerView.refreshComplete();
            isRefreshData = false;
        }

        if (isLoadingData) {
            currentPage++;
            data.addAll(list);
            recyclerView.loadMoreComplete();
            isLoadingData = false;
            if (list.isEmpty()) {
                recyclerView.setNoMore(true);
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadImagesFailure(String msg) {
        if (isRefreshData) {
            recyclerView.refreshComplete();
            isRefreshData = false;
        }

        if (isLoadingData) {
            recyclerView.loadMoreComplete();
            isLoadingData = false;
        }

        showToastMessage("加载失败，" + msg);
    }

    @Override
    public void onRefresh() {
        isRefreshData = true;
        presenter.loadImages(!data.isEmpty(), 0);
    }

    @Override
    public void onLoadMore() {
        isLoadingData = true;
        presenter.loadImages(!data.isEmpty(), currentPage + 1);
    }

    class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            ImageItem item = data.get(position);
            item.fixSize(imgWidth);

            if (!item.isLegal()) {
                return;
            }

            holder.tvTitle.setText(item.getTitle());

            ViewGroup.LayoutParams params = holder.ivImg.getLayoutParams();
            params.width = item.getFixWidth();
            params.height = item.getFixHeight();
            Glide.with(getActivity())
                    .load(item.getThumbLargeUrl())
                    .placeholder(new ColorDrawable(Color.TRANSPARENT))
                    .into(holder.ivImg);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivImg)
        ImageView ivImg;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition() - 1;
            if (pos >= 0 && pos < data.size()) {
            }
        }
    }

}
