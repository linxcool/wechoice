package com.linxcool.wechoice.data;

import com.linxcool.wechoice.data.entity.ImageList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by linxcool on 17/4/9.
 */

public interface ImageApi {

    @GET("channel/listjson?ie=utf8")
    Observable<ImageList> queryImageList(
            @Query("pn") int page,
            @Query("rn") int pageSize,
            @Query("tag1") String tag1,
            @Query("tag2") String tag2);


    @Headers("Cache-Force: true")
    @GET("channel/listjson?ie=utf8")
    Observable<ImageList> queryLocalImageList(
            @Query("pn") int page,
            @Query("rn") int pageSize,
            @Query("tag1") String tag1,
            @Query("tag2") String tag2);
}
