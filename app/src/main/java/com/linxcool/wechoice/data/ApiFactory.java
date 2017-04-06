package com.linxcool.wechoice.data;

import com.linxcool.andbase.retrofit.ParamsInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linxcool on 16/9/14.
 */
public class ApiFactory {

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new ParamsInterceptor())
            .build();

    private static final HashMap<String, Object> map = new HashMap<String, Object>() {
        {
            put("ArticleApi", new Retrofit.Builder()
                    .baseUrl("http://apicloud.mob.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
                    .create(ArticleApi.class));

            put("article", new Retrofit.Builder()
                    .baseUrl("http://v.juhe.cn/toutiao/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()
                    .create(ArticleApi.class));
        }
    };

    public static ArticleApi getArticleApi() {
        return (ArticleApi) map.get("article");
    }
}
