package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.rx.RxHelper;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.ApiFactory;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.data.entity.ImageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static android.R.id.list;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageListModel implements ImageListContract.Model {

    @Override
    public Observable<List<ImageItem>> loadNetworkImages(final String tag, int page) {
        Observable<List<ImageItem>> fromNetwork = ApiFactory.getImageApi()
                .queryImageList(page, ImageList.PAGE_SIZE, tag, "")
                .flatMap(new Function<ImageList, ObservableSource<List<ImageItem>>>() {
                    @Override
                    public ObservableSource<List<ImageItem>> apply(ImageList imageList) throws Exception {
                        return Observable.fromArray(imageList.getData());
                    }
                });
        return fromNetwork.compose(RxHelper.<List<ImageItem>>scheduleIo2UiThread());
    }

    @Override
    public Observable<List<ImageItem>> loadPreviousImages(final String tag, int page) {
        Observable<List<ImageItem>> fromNetwork = ApiFactory.getImageApi()
                .queryLocalImageList(page, ImageList.PAGE_SIZE, tag, "")
                .flatMap(new Function<ImageList, ObservableSource<List<ImageItem>>>() {
                    @Override
                    public ObservableSource<List<ImageItem>> apply(ImageList imageList) throws Exception {
                        return null;
                    }
                });
        return fromNetwork.compose(RxHelper.<List<ImageItem>>scheduleIo2UiThread());
    }

}
