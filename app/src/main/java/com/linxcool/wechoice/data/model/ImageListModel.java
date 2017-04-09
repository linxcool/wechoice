package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.rx.RxHelper;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.ApiFactory;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.data.entity.ImageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static android.R.id.list;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageListModel implements ImageListContract.Model {

    @Override
    public Observable<ImageList> loadNetworkImages(final String tag, int page) {
        Observable<ImageList> fromNetwork = ApiFactory.getImageApi()
                .queryImageList(page, ImageList.PAGE_SIZE, tag, "");
        return fromNetwork.compose(RxHelper.<ImageList>scheduleIo2UiThread());
    }

    @Override
    public Observable<ImageList> loadPreviousImages(final String tag, int page) {
        return loadNetworkImages(tag, page);
    }

}
