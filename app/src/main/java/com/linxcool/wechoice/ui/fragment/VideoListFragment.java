package com.linxcool.wechoice.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.VideoListContract;
import com.linxcool.wechoice.data.entity.VideoCategory;
import com.linxcool.wechoice.data.entity.VideoItem;
import com.linxcool.wechoice.data.model.VideoListModel;
import com.linxcool.wechoice.presenter.VideoListPresenter;
import com.linxcool.wechoice.ui.widget.LDecoration;
import com.linxcool.wechoice.ui.widget.NoAlphaItemAnimator;
import com.linxcool.wechoice.ui.widget.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static android.R.id.list;

public class VideoListFragment extends BaseFragment<VideoListPresenter, VideoListModel>
        implements VideoListContract.View, XRecyclerView.LoadingListener {

    @BindView(list)
    XRecyclerView recyclerView;

    VideoAdapter adapter;
    List<VideoItem> data = new ArrayList<>();
    boolean isRefreshData = false;
    boolean isLoadingData = false;
    int currentPage;

    public static VideoListFragment newInstance(VideoCategory category) {
        Bundle args = new Bundle();
        args.putString("cid", category.getCid());
        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initViews(LayoutInflater inflater, Bundle bundle) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        LDecoration decoration = new LDecoration(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                1,
                getColor(R.color.gray_split));
        decoration.setPadding(getDimension(R.dimen.def_padding));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new NoAlphaItemAnimator());
        recyclerView.setAdapter(adapter = new VideoAdapter());
        recyclerView.addItemDecoration(decoration);
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
    public void showVideos(List<VideoItem> list) {
        if (isRefreshData) {
            currentPage = 0;
            data.clear();
            data.addAll(list);
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
    public void showLoadVideosFailure(String msg) {
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
        presenter.loadVideos(!data.isEmpty(), 0);
    }

    @Override
    public void onLoadMore() {
        isLoadingData = true;
        presenter.loadVideos(!data.isEmpty(), currentPage + 1);
    }

    class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item_video, parent, false);
            return new VideoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            VideoItem item = data.get(position);

            holder.tvSummary.setText(item.getTopicName());
            Glide.with(getActivity()).load(item.getTopicImg()).into(holder.ivLogo);
            holder.tvPubTime.setText(item.getPtime());

            // set video player

            String text = item.getTitle();
            if (TextUtils.isEmpty(text)) {
                text = item.getDescription();
            }
            if (TextUtils.isEmpty(text)) {
                text = "";
            }
            holder.videoPlayer.setUp(
                    item.getMp4_url(),
                    JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    text);
            Glide.with(getActivity()).load(item.getCover())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    //.error(R.drawable.ic_empty_picture)
                    .crossFade().into(holder.videoPlayer.thumbImageView);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.videoPlayer)
        JCVideoPlayerStandard videoPlayer;
        @BindView(R.id.ivLogo)
        ImageView ivLogo;
        @BindView(R.id.tvSummary)
        TextView tvSummary;
        @BindView(R.id.tvPubTime)
        TextView tvPubTime;

        VideoViewHolder(View view) {
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
