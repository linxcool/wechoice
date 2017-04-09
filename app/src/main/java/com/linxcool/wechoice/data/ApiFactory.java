package com.linxcool.wechoice.data;

import android.content.Context;

import com.linxcool.andbase.retrofit.ParamsInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linxcool on 16/9/14.
 */
public class ApiFactory {

    private static OkHttpClient httpClient;
    private static OkHttpClient cacheClient;
    private static HashMap<String, Object> map;

    public static void regist(Context context) {
        ParamsInterceptor.setLogable(true);
        ArticleDataSource.regist(context);

        httpClient = createHttpClient(context);
        cacheClient = createCacheClient(context);

        map = new HashMap<>();

        map.put("article", new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/toutiao/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(ArticleApi.class));

        map.put("video", new Retrofit.Builder()
                .baseUrl("http://c.m.163.com/nc/video/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(cacheClient)
                .build()
                .create(VideoApi.class));

        map.put("image", new Retrofit.Builder()
                .baseUrl("http://image.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(ImageApi.class));
    }

    private static OkHttpClient createHttpClient(Context context) {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new ParamsInterceptor())
                .build();
    }

    private static OkHttpClient createCacheClient(Context context) {
        File cacheFile = new File(context.getCacheDir(), "retrofit");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new ParamsInterceptor())
                .addInterceptor(new ApiCacheInterceptor(context))
                .addNetworkInterceptor(new ApiCacheInterceptor(context))
                .cache(cache)
                .build();
    }

    public static ArticleApi getArticleApi() {
        return (ArticleApi) map.get("article");
    }

    public static VideoApi getVideoApi() {
        return (VideoApi) map.get("video");
    }

    public static ImageApi getImageApi() {
        return (ImageApi) map.get("image");
    }
}
