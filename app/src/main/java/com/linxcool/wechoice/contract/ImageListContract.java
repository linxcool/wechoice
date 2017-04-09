package com.linxcool.wechoice.contract;

import com.linxcool.andbase.mvp.BaseContract;
import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.data.entity.ImageList;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/3/16.
 */

public interface ImageListContract extends BaseContract {

    interface View extends BaseView<BaseView> {

        String getCategoryId();

        List<ImageItem> getImages();

        void showImages(List<ImageItem> list);

        void showLoadImagesFailure(String msg);

    }

    interface Presenter extends BasePresenter {

        void loadImages(boolean fromNetwork, int page);

    }

    interface Model extends BaseModel {

        Observable<ImageList> loadNetworkImages(String tag, int page);

        Observable<ImageList> loadPreviousImages(String tag, int page);

    }

}
