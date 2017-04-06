package com.linxcool.wechoice.data;

import com.linxcool.andbase.retrofit.Reply;
import com.linxcool.wechoice.AppConstant;
import com.linxcool.wechoice.data.entity.ArticleList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.key;

/**
 * Created by linxcool on 17/3/16.
 */

public interface ArticleApi {

    @GET("index?key=" + AppConstant.JUHE_NEWS_API_KEY)
    Observable<ArticleList> queryArticleList(@Query("type") String cid);

}
