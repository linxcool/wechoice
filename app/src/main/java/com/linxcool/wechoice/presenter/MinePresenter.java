package com.linxcool.wechoice.presenter;

import com.linxcool.wechoice.contract.MineContract;

/**
 * Created by linxcool on 17/3/16.
 */

public class MinePresenter implements MineContract.Presenter {

    MineContract.View view;
    MineContract.Model model;

    public MinePresenter(MineContract.View view, MineContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
    }
}
