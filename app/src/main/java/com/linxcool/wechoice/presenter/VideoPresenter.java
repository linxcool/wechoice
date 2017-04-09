package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.ReplyObserver;
import com.linxcool.wechoice.contract.VideoContract;
import com.linxcool.wechoice.data.entity.VideoCategory;

import java.util.List;

/**
 * Created by linxcool on 17/3/16.
 */

public class VideoPresenter implements VideoContract.Presenter {

    VideoContract.View view;
    VideoContract.Model model;

    public VideoPresenter(VideoContract.View view, VideoContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        this.loadCategorys();
    }

    @Override
    public void loadCategorys() {
        model.loadCategorys().subscribe(new ReplyObserver<List<VideoCategory>>() {
            @Override
            public void onSuccess(List<VideoCategory> categories) {
                view.showCategorys(categories);
            }
            @Override
            public void onFailure(int code, String msg) {
                view.showToastMessage(msg);
            }
        });
    }
}
