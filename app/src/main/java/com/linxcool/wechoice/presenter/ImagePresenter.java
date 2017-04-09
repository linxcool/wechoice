package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.ReplyObserver;
import com.linxcool.wechoice.contract.ImageContract;
import com.linxcool.wechoice.data.entity.ImageCategory;

import java.util.List;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImagePresenter implements ImageContract.Presenter {

    ImageContract.View view;
    ImageContract.Model model;

    public ImagePresenter(ImageContract.View view, ImageContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        this.loadCategorys();
    }

    @Override
    public void loadCategorys() {
        model.loadCategorys().subscribe(new ReplyObserver<List<ImageCategory>>() {
            @Override
            public void onSuccess(List<ImageCategory> categories) {
                view.showCategorys(categories);
            }
            @Override
            public void onFailure(int code, String msg) {
                view.showToastMessage(msg);
            }
        });
    }
}
