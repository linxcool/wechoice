package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.ReplyObserver;
import com.linxcool.wechoice.contract.ArticleContract;
import com.linxcool.wechoice.data.entity.ArticleCategory;

import java.util.List;

/**
 * Created by linxcool on 17/3/16.
 */

public class ArticlePresenter implements ArticleContract.Presenter {

    ArticleContract.View view;
    ArticleContract.Model model;

    public ArticlePresenter(ArticleContract.View view, ArticleContract.Model model) {
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
