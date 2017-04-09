package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.wechoice.contract.ArticleListContract;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.data.entity.ArticleList;

import java.util.List;

/**
 * Created by linxcool on 17/3/16.
 */

public class ArticleListPresenter implements ArticleListContract.Presenter {

    ArticleListContract.View view;
    ArticleListContract.Model model;

    public ArticleListPresenter(ArticleListContract.View view, ArticleListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {}

    @Override
    public void loadArticles(final boolean fromNetwork) {
        SimpleObserver observer = new SimpleObserver<ArticleList>() {
            @Override
            public void onNext(ArticleList value) {
                if (value.getErrorCode() != 0 || value.getReply() == null) {
                    view.showLoadArticlesFailure(value.getReason() + "，错误码：" + value.getErrorCode());
                    return;
                }
                List<ArticleItem> data = value.getReply().getData();
                if (data == null) {
                    view.showLoadArticlesFailure("服务异常，无数据返回");
                    return;
                }
                if(data.isEmpty() && !fromNetwork) {
                    loadArticles(true);
                    return;
                }
                view.showArticles(value.getReply().getData());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showLoadArticlesFailure("请检查网络或重试");
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
