package com.linxcool.wechoice.presenter;

import android.util.Log;

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
    public void start() {
    }

    @Override
    public void loadImages(final boolean fromNetwork, final int page) {
        Log.e("AAAAA", ">>>>load image + " + page + " -> " + fromNetwork);

        SimpleObserver observer = new SimpleObserver<ImageList>() {
            @Override
            public void onNext(ImageList value) {
                if (value.getReturnNumber() <= 0) {
                    view.showToastMessage("请检查网络或重试");
                } else {
                    view.showImages(value.getData());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (fromNetwork) view.showToastMessage("请检查网络或重试");
                else loadImages(true, page);
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
