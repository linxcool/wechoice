package com.linxcool.wechoice.presenter;

import com.linxcool.andbase.retrofit.SimpleObserver;
import com.linxcool.wechoice.contract.VideoListContract;
import com.linxcool.wechoice.data.entity.VideoItem;

import java.util.List;

/**
 * Created by linxcool on 17/3/16.
 */

public class VideoListPresenter implements VideoListContract.Presenter {

    VideoListContract.View view;
    VideoListContract.Model model;

    public VideoListPresenter(VideoListContract.View view, VideoListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
    }

    @Override
    public void loadVideos(final boolean fromNetwork, final int page) {
        SimpleObserver observer = new SimpleObserver<List<VideoItem>>() {
            @Override
            public void onNext(List<VideoItem> value) {
                view.showVideos(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (fromNetwork) view.showLoadVideosFailure("请检查网络或重试");
                else loadVideos(true, page);
            }

        };

        String cid = view.getCategoryId();

        if (fromNetwork) {
            model.loadNetworkVideos(cid, page).subscribe(observer);
        } else {
            model.loadPreviousVideos(cid, page).subscribe(observer);
        }
    }
}
