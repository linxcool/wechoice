package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.wechoice.contract.ArticleListContract;
import com.linxcool.wechoice.data.CollectDataCache;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.data.entity.ArticleList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

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
    public void start() {
    }

    @Override
    public void loadArticles(boolean fromNetwork) {
        String cid = view.getCategoryId();
        if (CollectDataCache.isCollectCategory(cid)) {
            loadCollectArticles(fromNetwork);
        } else {
            loadNormalArticles(fromNetwork, cid);
        }
    }

    public void loadNormalArticles(final boolean fromNetwork, String cid) {
        Observer observer = new SimpleObserver<ArticleList>() {
            @Override
            public void onNext(ArticleList value) {
                int errorCode = value.getErrorCode();
                String errorReason = value.getReason();
                Reply<List<ArticleItem>> reply = value.getReply();

                if (errorCode != 0 || reply == null) {
                    view.showLoadArticlesFailure(errorReason + "，错误码：" + errorCode);
                    return;
                }

                List<ArticleItem> data = reply.getData();
                if (data == null) {
                    view.showLoadArticlesFailure("服务异常，无数据返回");
                    return;
                }

                if (data.isEmpty() && !fromNetwork) {
                    loadArticles(true);
                    return;
                }

                view.showArticles(data);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showLoadArticlesFailure("请检查网络或重试");
            }

        };

        if (fromNetwork) {
            model.loadNetworkArticles(cid).subscribe(observer);
        } else {
            int curMinId = view.getMinArticleId();
            model.loadPreviousArticles(cid, curMinId).subscribe(observer);
        }
    }

    public void loadCollectArticles(boolean fromNetwork) {
        if (fromNetwork) {
            view.showArticles(new ArrayList<ArticleItem>());
        } else {
            int curMinId = view.getMinArticleId();
            if (curMinId < 0) {
                view.showArticles(CollectDataCache.getArticles());
            } else {
                view.showArticles(new ArrayList<ArticleItem>());
            }
        }
    }
}
