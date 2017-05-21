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

    String[] MVTAGS = {
            "唯美", "长腿", "诱惑", "气质", "可爱",
            "性感", "车模", "写真", "素颜",
            "小清新", "嫩萝莉", "非主流", "cosplay",
            "西洋美人", "古典美女", "宅男女神",
            "网络美女", "真人美女秀场"
    };

    String[] BZTAGS = {
            "唯美", "风景", "静物", "美女", "宠物",
            "明星", "游戏", "影视", "动漫"
    };

    @Override
    public Observable<Reply<List<ImageCategory>>> loadCategorys() {
        List<ImageCategory> categories = new ArrayList<>();
        for (int i = 0; i < MVTAGS.length; i++) {
            categories.add(new ImageCategory("美女", MVTAGS[i]));
        }
        return Observable.just(Reply.success(categories)).observeOn(AndroidSchedulers.mainThread());
    }

}
