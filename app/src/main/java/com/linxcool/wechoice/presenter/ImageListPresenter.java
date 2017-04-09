package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.entity.ImageList;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageListPresenter implements ImageListContract.Presenter {

    ImageListContract.View view;
    ImageListContract.Model model;

    public ImageListPresenter(ImageListContract.View view, ImageListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {}

    @Override
    public void loadImages(boolean fromNetwork, int page) {
        SimpleObserver observer = new SimpleObserver<ImageList>() {
            @Override
            public void onNext(ImageList value) {
                if (value.getData() == null) {
                    view.showLoadImagesFailure("服务异常，无数据返回");
                    return;
                }
                view.showImages(value.getData());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showLoadImagesFailure("请检查网络！");
            }

        };

        String cid = view.getCategoryId();

        if (fromNetwork) {
            model.loadNetworkImages(cid, page).subscribe(observer);
        } else {
            model.loadPreviousImages(cid, page).subscribe(observer);
        }
    }
}
