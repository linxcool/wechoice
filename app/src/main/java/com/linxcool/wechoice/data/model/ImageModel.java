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
        categories.add(new ImageCategory("美女"));
        categories.add(new ImageCategory("静物"));
        categories.add(new ImageCategory("唯美"));
        return Observable.just(Reply.success(categories)).observeOn(AndroidSchedulers.mainThread());
    }

}
