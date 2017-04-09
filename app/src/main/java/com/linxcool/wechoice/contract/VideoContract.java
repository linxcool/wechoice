package com.linxcool.wechoice.contract;

import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.data.entity.VideoCategory;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/4/8.
 */

public interface VideoContract {

    interface View extends BaseView<BaseView> {

        void showCategorys(List<VideoCategory> categories);

    }

    interface Presenter extends BasePresenter {

        void loadCategorys();

    }

    interface Model extends BaseModel {

        Observable<Reply<List<VideoCategory>>> loadCategorys();

    }

}
