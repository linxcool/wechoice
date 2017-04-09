package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.contract.VideoContract;
import com.linxcool.wechoice.data.entity.VideoCategory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by linxcool on 17/4/8.
 */

public class VideoModel implements VideoContract.Model {

    @Override
    public Observable<Reply<List<VideoCategory>>> loadCategorys() {
        List<VideoCategory> categories = new ArrayList<>();
        categories.add(new VideoCategory("V9LG4B3A0", "热点"));
        categories.add(new VideoCategory("V9LG4E6VR", "搞笑"));
        categories.add(new VideoCategory("V9LG4CHOR", "娱乐"));
        categories.add(new VideoCategory("00850FRB", "精品"));
        return Observable.just(Reply.success(categories)).observeOn(AndroidSchedulers.mainThread());
    }

}
