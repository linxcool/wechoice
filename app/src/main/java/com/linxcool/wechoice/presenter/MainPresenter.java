package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.ReplyObserver;
import com.linxcool.andbase.rx.RxCache;
import com.linxcool.wechoice.contract.MainContract;
import com.linxcool.wechoice.data.entity.ArticleCategory;

import java.util.List;

/**
 * Created by linxcool on 17/3/16.
 */

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    MainContract.Model model;

    public MainPresenter(MainContract.View view, MainContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        this.loadCategorys();
    }

    @Override
    public void loadCategorys() {
        model.loadCategorys().subscribe(new ReplyObserver<List<ArticleCategory>>() {
            @Override
            public void onSuccess(List<ArticleCategory> categories) {
                view.showCategorys(categories);
            }
            @Override
            public void onFailure(int code, String msg) {
                view.showToastMessage(msg);
            }
        });
    }
}
