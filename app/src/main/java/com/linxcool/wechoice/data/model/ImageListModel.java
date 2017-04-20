package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.rx.RxHelper;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.ApiFactory;
import com.linxcool.wechoice.data.ImageApi;
import com.linxcool.wechoice.data.entity.ImageItem;
import com.linxcool.wechoice.data.entity.ImageList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static android.R.attr.tag;
import static android.R.id.list;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageListModel implements ImageListContract.Model {

    @Override
    public Observable<List<ImageItem>> loadImages(boolean fromNetwork, String tag, int page) {
        ImageApi api = ApiFactory.getImageApi();
        Observable<ImageList> observable;
        String tag2 = "";

        if (fromNetwork) observable = api.queryImageList(page, ImageList.PAGE_SIZE, tag, tag2);
        else observable = api.queryLocalImageList(page, ImageList.PAGE_SIZE, tag, tag2);

        return observable.flatMap(new Function<ImageList, ObservableSource<List<ImageItem>>>() {
            @Override
            public ObservableSource<List<ImageItem>> apply(ImageList imageList) throws Exception {
                if (imageList.getReturnNumber() == 0) {
                    return Observable.fromArray();
                }
                return Observable.fromArray(imageList.getData());
            }
        }).compose(RxHelper.<List<ImageItem>>scheduleIo2UiThread());
    }

}
