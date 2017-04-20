package com.linxcool.wechoice.presenter;

import android.util.Log;

import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.andbase.util.LogUtil;
import com.linxcool.wechoice.contract.ImageListContract;
import com.linxcool.wechoice.data.entity.ImageList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;

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
        LogUtil.iFormat("load %s images page %d, fromNetwork %s", view.getCategoryId(), page, String.valueOf(fromNetwork));

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
                if (fromNetwork) {
                    view.showToastMessage("请检查网络或重试");
                    e.printStackTrace();
                } else {
                    LogUtil.eFormat("can't find data(%s) from cache", view.getCategoryId());
                    loadImages(true, page);
                }
            }

        };

        Predicate<ImageList> filter = new Predicate<ImageList>() {
            @Override
            public boolean test(ImageList value) throws Exception {
                return false;
            }
        };

        String cid = view.getCategoryId();

        if (fromNetwork) {
            model.loadNetworkImages(cid, page).filter(filter).subscribe(observer);
        } else {
            model.loadPreviousImages(cid, page).filter(filter).subscribe(observer);
        }
    }
}
