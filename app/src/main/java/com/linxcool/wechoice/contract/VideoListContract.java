package com.linxcool.wechoice.contract;

import com.linxcool.andbase.mvp.BaseContract;
import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.wechoice.data.entity.VideoItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/3/16.
 */

public interface VideoListContract extends BaseContract {

    interface View extends BaseView<BaseView> {

        String getCategoryId();

        void showVideos(List<VideoItem> list);

        void showLoadVideosFailure(String msg);

    }

    interface Presenter extends BasePresenter {

        void loadVideos(boolean fromNetwork, int page);

    }

    interface Model extends BaseModel {

        Observable<List<VideoItem>> loadNetworkVideos(String cid, int startPage);

        Observable<List<VideoItem>> loadPreviousVideos(String cid, int startPage);

    }

}
