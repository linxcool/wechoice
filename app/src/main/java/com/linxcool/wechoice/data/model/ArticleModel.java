package com.linxcool.wechoice.data.model;

import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.contract.ArticleContract;
import com.linxcool.wechoice.data.entity.ArticleCategory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by linxcool on 17/3/16.
 */

public class ArticleModel implements ArticleContract.Model {

    @Override
    public Observable<Reply<List<ArticleCategory>>> loadCategorys() {
        List<ArticleCategory> categories = new ArrayList<>();
        categories.add(new ArticleCategory("top", "头条"));
        categories.add(new ArticleCategory("shehui", "社会"));
        categories.add(new ArticleCategory("yule", "娱乐"));
        categories.add(new ArticleCategory("shishang", "时尚"));
        categories.add(new ArticleCategory("guonei", "国内"));
        categories.add(new ArticleCategory("guoji", "国际"));
        categories.add(new ArticleCategory("tiyu", "体育"));
        categories.add(new ArticleCategory("junshi", "军事"));
        categories.add(new ArticleCategory("keji", "科技"));
        categories.add(new ArticleCategory("caijing", "财经"));
        categories.add(new ArticleCategory("qita", "其他"));
        return Observable.just(Reply.success(categories)).observeOn(AndroidSchedulers.mainThread());
    }

}
