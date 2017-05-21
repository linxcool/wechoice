package com.linxcool.wechoice.contract;

import com.linxcool.andbase.mvp.BaseContract;
import com.linxcool.andbase.mvp.BaseModel;
import com.linxcool.andbase.mvp.BasePresenter;
import com.linxcool.andbase.mvp.BaseView;
import com.linxcool.wechoice.data.entity.ImageItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by linxcool on 17/3/16.
 */

public interface ImageListContract extends BaseContract {

    interface View extends BaseView<BaseView> {

        String getImageCol();

        String getImageTag();

        List<ImageItem> getImages();

        void showImages(List<ImageItem> list);

        void showLoadImagesFailure(String msg);

    }

    interface Presenter extends BasePresenter {

        void loadImages(boolean fromNetwork, int page);

    }

    interface Model extends BaseModel {

        Observable<List<ImageItem>> loadImages(boolean fromNetwork, String col, String tag, int page);

    }

}
