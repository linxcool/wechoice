package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.rx.RxHelper;
import com.linxcool.wechoice.contract.VideoListContract;
import com.linxcool.wechoice.data.ApiFactory;
import com.linxcool.wechoice.data.entity.VideoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by linxcool on 17/3/16.
 */

public class VideoListModel implements VideoListContract.Model {

    @Override
    public Observable<List<VideoItem>> loadNetworkVideos(final String cid, int startPage) {
        Observable<List<VideoItem>> fromNetwork = ApiFactory.getVideoApi()
                .queryVedioList(cid, startPage)
                .flatMap(new Function<Map<String, List<VideoItem>>, ObservableSource<List<VideoItem>>>() {
                    @Override
                    public ObservableSource<List<VideoItem>> apply(Map<String, List<VideoItem>> map) throws Exception {
                        return Observable.fromArray(map.get(cid));
                    }
                });
        return fromNetwork.compose(RxHelper.<List<VideoItem>>scheduleIo2UiThread());
    }

    @Override
    public Observable<List<VideoItem>> loadPreviousVideos(final String cid, int startPage) {
        Observable<List<VideoItem>> fromNetwork = ApiFactory.getVideoApi()
                .queryCacheVedioList(cid, startPage)
                .flatMap(new Function<Map<String, List<VideoItem>>, ObservableSource<List<VideoItem>>>() {
                    @Override
                    public ObservableSource<List<VideoItem>> apply(Map<String, List<VideoItem>> map) throws Exception {
                        return Observable.fromArray(map.get(cid));
                    }
                });
        return fromNetwork.compose(RxHelper.<List<VideoItem>>scheduleIo2UiThread());
    }

}
