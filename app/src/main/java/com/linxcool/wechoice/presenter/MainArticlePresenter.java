package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.wechoice.contract.MainArticleContract;
import com.linxcool.wechoice.data.entity.ArticleList;

/**
 * Created by linxcool on 17/3/16.
 */

public class MainArticlePresenter implements MainArticleContract.Presenter {

    MainArticleContract.View view;
    MainArticleContract.Model model;

    public MainArticlePresenter(MainArticleContract.View view, MainArticleContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {}

    @Override
    public void loadArticles(boolean fromNetwork) {
        SimpleObserver observer = new SimpleObserver<ArticleList>() {
            @Override
            public void onNext(ArticleList value) {
                if (value.getErrorCode() != 0) {
                    view.showLoadArticlesFailure(value.getReason() + "，错误码：" + value.getErrorCode());
                    return;
                }
                if (value.getReply() == null || value.getReply().getData() == null) {
                    view.showLoadArticlesFailure("服务异常，无数据返回");
                    return;
                }
                view.showArticles(value.getReply().getData());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showLoadArticlesFailure("请检查网络！");
            }

        };

        String cid = view.getCategoryId();
        int curMinId = view.getMinArticleId();

        if (fromNetwork) {
            model.loadNetworkArticles(cid).subscribe(observer);
        } else {
            model.loadPreviousArticles(cid, curMinId).subscribe(observer);
        }
    }
}
