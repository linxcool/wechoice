package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.contract.ImageContract;
import com.linxcool.wechoice.data.entity.ImageCategory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by linxcool on 17/3/16.
 */

public class ImageModel implements ImageContract.Model {

    @Override
    public Observable<Reply<List<ImageCategory>>> loadCategorys() {
        List<ImageCategory> categories = new ArrayList<>();
        categories.add(new ImageCategory("壁纸"));
        categories.add(new ImageCategory("美女"));
        categories.add(new ImageCategory("明星"));
        categories.add(new ImageCategory("动漫"));
        categories.add(new ImageCategory("搞笑"));
        categories.add(new ImageCategory("宠物"));
        categories.add(new ImageCategory("摄影"));
        return Observable.just(Reply.success(categories)).observeOn(AndroidSchedulers.mainThread());
    }

}
