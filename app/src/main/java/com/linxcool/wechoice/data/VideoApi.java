package com.linxcool.wechoice.data;

import com.linxcool.wechoice.data.entity.VideoItem;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by linxcool on 17/4/8.
 */

public interface VideoApi {

    @Headers("Cache-Control: public, max-age=3600")
    @GET("list/{type}/n/{page}-10.html")
    Observable<Map<String, List<VideoItem>>> queryVedioList(
            @Path("type") String type,
            @Path("page") int startPage);

    @Headers({
            "Cache-Force: true",
            "Cache-Control: public, max-age=3600"
    })
    @GET("list/{type}/n/{page}-10.html")
    Observable<Map<String, List<VideoItem>>> queryCacheVedioList(
            @Path("type") String type,
            @Path("page") int startPage);
}
