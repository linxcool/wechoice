package com.linxcool.wechoice.ui.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.MineContract;
import com.linxcool.wechoice.data.model.MineModel;
import com.linxcool.wechoice.presenter.MinePresenter;
import com.linxcool.wechoice.ui.CollectListActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

public class MineFragment extends BaseFragment<MinePresenter, MineModel> implements MineContract.View {


    @BindView(R.id.ivHead)
    ImageView ivHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvNote)
    TextView tvNote;
    @BindView(R.id.btnNews)
    TextView btnNews;
    @BindView(R.id.btnImages)
    TextView btnImages;
    @BindView(R.id.btnAbout)
    TextView btnAbout;

    @Override
    protected int getLayout(Activity activity) {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews(LayoutInflater inflater, Bundle bundle) {

    }

    @OnClick({R.id.btnNews, R.id.btnImages, R.id.btnAbout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNews:
                toCollectArticlesUi();
                break;
            case R.id.btnImages:
                toCollectImagesUi();
                break;
            case R.id.btnAbout:
                showAboutUi();
                break;
        }
    }

    @Override
    public void toCollectArticlesUi() {
        Intent intent = new Intent(getActivity(), CollectListActivity.class);
        intent.putExtra(CollectListActivity.KEY_TYPE, CollectListActivity.TYPE_ARTICLE);
        startActivity(intent);
    }

    @Override
    public void toCollectImagesUi() {
        Intent intent = new Intent(getActivity(), CollectListActivity.class);
        intent.putExtra(CollectListActivity.KEY_TYPE, CollectListActivity.TYPE_IMAGE);
        startActivity(intent);
    }

    @Override
    public void showAboutUi() {
        new AlertDialog.Builder(getActivity())
                .setTitle("关注有料")
                .setMessage(Html.fromHtml("搜索微信公众号“<font color='red'>有料闲聊</font>”，关注微信号i37086，每日福利及精彩内容邀您体验！"))
                .setNegativeButton("确定", null)
                .show();
    }
}
