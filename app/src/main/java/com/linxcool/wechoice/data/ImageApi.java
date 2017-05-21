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

    @GET("data/imgs?p=channel&from=1")
    Observable<ImageList> queryImageList(
            @Query("pn") int page,
            @Query("rn") int pageSize,
            @Query("col") String col,
            @Query("tag") String tag);


    @Headers("Cache-Force: true")
    @GET("data/imgs?p=channel&from=1")
    Observable<ImageList> queryLocalImageList(
            @Query("pn") int page,
            @Query("rn") int pageSize,
            @Query("col") String col,
            @Query("tag") String tag);
}
