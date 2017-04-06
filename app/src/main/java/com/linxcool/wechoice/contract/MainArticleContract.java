package com.linxcool.wechoice.contract;

import android.app.Activity;

import com.linxcool.andbase.mvp.BaseContract;
import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.wechoice.data.entity.ArticleItem;
import com.linxcool.wechoice.data.entity.ArticleList;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/3/16.
 */

public interface MainArticleContract extends BaseContract {

    interface View extends BaseView<BaseView> {

        String getCategoryId();

        int getMinArticleId();

        void showArticles(List<ArticleItem> list);

        void showLoadArticlesFailure(String msg);

    }

    interface Presenter extends BasePresenter {

        void loadArticles(boolean fromNetwork);

    }

    interface Model extends BaseModel {

        Observable<ArticleList> loadNetworkArticles(String cid);

        Observable<ArticleList> loadPreviousArticles(String cid, int curMinId);

    }

}
