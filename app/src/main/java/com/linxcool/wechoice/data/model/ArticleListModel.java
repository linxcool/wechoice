package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.rx.RxHelper;
import com.linxcool.wechoice.contract.ArticleListContract;
import com.linxcool.wechoice.data.ApiFactory;
import com.linxcool.wechoice.data.ArticleDataSource;
import com.linxcool.wechoice.data.entity.ArticleList;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/3/16.
 */

public class ArticleListModel implements ArticleListContract.Model {

    @Override
    public Observable<ArticleList> loadNetworkArticles(String cid) {
        Observable<ArticleList> fromNetwork = ApiFactory.getArticleApi()
                .queryArticleList(cid);
        return ArticleDataSource.mapToDbCache(cid, fromNetwork)
                .compose(RxHelper.<ArticleList>scheduleIo2UiThread());
    }

    @Override
    public Observable<ArticleList> loadPreviousArticles(String cid, int curMinId) {
        if (curMinId == -1) {
            curMinId = ArticleDataSource.getMaxId(cid);
        }
        return ArticleDataSource.loadPrevious(cid, curMinId)
                .compose(RxHelper.<ArticleList>scheduleIo2UiThread());
    }

}
