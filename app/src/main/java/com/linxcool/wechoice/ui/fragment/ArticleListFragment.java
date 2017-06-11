package com.linxcool.wechoice.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.linxcool.andbase.util.TextUtil;
import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.ArticleListContract;
import com.linxcool.wechoice.data.entity.ArticleCategory;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.data.model.ArticleListModel;
import com.linxcool.wechoice.presenter.ArticleListPresenter;
import com.linxcool.wechoice.ui.WebActivity;
import com.linxcool.wechoice.ui.widget.LDecoration;
import com.linxcool.wechoice.ui.widget.NoAlphaItemAnimator;
import com.linxcool.wechoice.ui.widget.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListFragment extends BaseFragment<ArticleListPresenter, ArticleListModel>
        implements ArticleListContract.View, XRecyclerView.LoadingListener {

    @BindView(R.id.list)
    XRecyclerView recyclerView;

    ArticleAdapter adapter;
    List<ArticleItem> data = new ArrayList<>();
    boolean isRefreshData = false;
    boolean isLoadingData = false;

    public static ArticleListFragment newInstance(ArticleCategory category) {
        Bundle args = new Bundle();
        args.putString("cid", category.getCid());
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_article_list;
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
        recyclerView.setAdapter(adapter = new ArticleAdapter());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLoadingListener(this);
        recyclerView.setRefreshProgressStyle(ProgressStyle.SquareSpin);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
    }

    @Override
    protected void onInitComplete() {
        recyclerView.load();
    }

    @Override
    public String getCategoryId() {
        return getArguments().getString("cid");
    }

    @Override
    public int getMinArticleId() {
        if (!data.isEmpty()) {
            return data.get(data.size() - 1).getId();
        }
        return -1;
    }

    @Override
    public void showArticles(List<ArticleItem> list) {
        if (isRefreshData) {
            data.addAll(0, list);
            recyclerView.refreshComplete();
            isRefreshData = false;
        }

        if (isLoadingData) {
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
    public void showLoadArticlesFailure(String msg) {
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
        presenter.loadArticles(true);
    }

    @Override
    public void onLoadMore() {
        isLoadingData = true;
        presenter.loadArticles(false);
    }

    class ArticleAdapter extends RecyclerView.Adapter<BaseViewHolder> {

        static final int TYPE_1 = 1;
        static final int TYPE_3 = 3;

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_1: {
                    View view = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.list_item_article_1, parent, false);
                    return new ArticleViewHolder(view);
                }
                case TYPE_3: {
                    View view = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.list_item_article_3, parent, false);
                    return new ArticleViewHolder3(view);
                }
                default: {
                    return null;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            ArticleItem item = data.get(position);
            if (TextUtil.notEmpty(item.getThumbnails3())) {
                return TYPE_3;
            }
            return TYPE_1;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            ArticleItem item = data.get(position);
            holder.tvTitle.setText(item.getTitle());
            holder.tvSummary.setText(item.getAuthory());
            holder.tvPubTime.setText(item.getPubTime());

            if (holder instanceof ArticleViewHolder) {
                ArticleViewHolder holder1 = (ArticleViewHolder) holder;
                Glide.with(getActivity()).load(item.getThumbnails()).into(holder1.ivIcon);
            } else if (holder instanceof ArticleViewHolder3) {
                ArticleViewHolder3 holder3 = (ArticleViewHolder3) holder;
                Glide.with(getActivity()).load(item.getThumbnails()).into(holder3.ivIcon);
                Glide.with(getActivity()).load(item.getThumbnails2()).into(holder3.ivIcon2);
                Glide.with(getActivity()).load(item.getThumbnails3()).into(holder3.ivIcon3);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }

    class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvSummary)
        TextView tvSummary;
        @BindView(R.id.tvPubTime)
        TextView tvPubTime;

        BaseViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition() - 1;
            if (pos >= 0 && pos < data.size()) {
                ArticleItem item = data.get(pos);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        }
    }

    class ArticleViewHolder extends BaseViewHolder {

        @BindView(R.id.ivIcon)
        ImageView ivIcon;

        ArticleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ArticleViewHolder3 extends BaseViewHolder {

        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.ivIcon2)
        ImageView ivIcon2;
        @BindView(R.id.ivIcon3)
        ImageView ivIcon3;

        ArticleViewHolder3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
