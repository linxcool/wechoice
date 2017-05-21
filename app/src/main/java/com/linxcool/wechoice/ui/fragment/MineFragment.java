package com.linxcool.wechoice.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linxcool.wechoice.R;
import com.linxcool.wechoice.base.BaseFragment;
import com.linxcool.wechoice.contract.MineContract;
import com.linxcool.wechoice.data.model.MineModel;
import com.linxcool.wechoice.presenter.MinePresenter;

import butterknife.BindView;
import butterknife.OnClick;

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
                break;
            case R.id.btnImages:
                break;
            case R.id.btnAbout:
                break;
        }
    }
}
