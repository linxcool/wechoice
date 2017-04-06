package com.linxcool.wechoice.contract;

import android.app.Activity;

import com.linxcool.andbase.mvp.BaseContract;
import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.data.entity.ArticleCategory;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/3/16.
 */

public interface MainContract extends BaseContract {

    interface View extends BaseView<BaseView> {

        Activity getActivity();

        void showCategorys(List<ArticleCategory> categories);

    }

    interface Presenter extends BasePresenter {

        void loadCategorys();

    }

    interface Model extends BaseModel {

        Observable<Reply<List<ArticleCategory>>> loadCategorys();

    }


}
